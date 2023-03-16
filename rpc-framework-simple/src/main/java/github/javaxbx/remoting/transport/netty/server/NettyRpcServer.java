package github.javaxbx.remoting.transport.netty.server;

import github.javaxbx.autoconfigure.SpringRpcProperties;
import github.javaxbx.config.CustomShutdownHook;
import github.javaxbx.config.RpcServiceConfig;
import github.javaxbx.enums.RpcConfigEnum;
import github.javaxbx.provider.ServiceProvider;
import github.javaxbx.remoting.transport.netty.codec.RpcMessageDecoder;
import github.javaxbx.remoting.transport.netty.codec.RpcMessageEncoder;
import github.javaxbx.utils.CustomConfig;
import github.javaxbx.utils.RuntimeUtil;
import github.javaxbx.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Server. Receive the client message, call the corresponding method according to the client message,
 * and then return the result to the client.
 *
 * @Author xiaobiaoxu
 * @Date 2023年03月13日 12:37
 */
@Slf4j
@Component
public class NettyRpcServer {

    @Autowired
    private SpringRpcProperties springRpcProperties;
    @Autowired
    private NettyRpcServerHandler nettyRpcServerHandler;
    @Autowired()
    @Qualifier("zkServiceProviderImpl")
    private ServiceProvider serviceProvider;
//    private ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
            RuntimeUtil.cpus() * 2,
            ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
    );
    @SneakyThrows
    public void start() {
        CustomShutdownHook.getCustomShutdownHook().clearAll(springRpcProperties.getPort());
        String host = InetAddress.getLocalHost().getHostAddress();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 当客户端第一次进行请求的时候才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 30 秒之内没有收到客户端请求的话就关闭连接
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            p.addLast(new RpcMessageEncoder());
                            p.addLast(new RpcMessageDecoder());
                            p.addLast(serviceHandlerGroup, nettyRpcServerHandler);
                        }
                    });
            Properties properties = CustomConfig.getProperties();
            Integer port = properties != null && properties.getProperty(RpcConfigEnum.PORT.getPropertyValue()) != null
                        ? Integer.valueOf(properties.getProperty(RpcConfigEnum.PORT.getPropertyValue())) : springRpcProperties.getPort();
            // 绑定端口，同步等待绑定成功
            ChannelFuture f = b.bind(host, port).sync();
            // 等待服务端监听端口关闭
            if (f.isSuccess()) {
                log.info("Service started successfully: {}:{}", host, port);
            } else {
                log.error("Service started failure: {}:{}", host, port);
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("occur exception when start server:", e);
        } finally {
            log.error("shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }
    }

}
