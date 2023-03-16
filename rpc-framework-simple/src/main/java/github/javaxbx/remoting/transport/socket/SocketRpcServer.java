package github.javaxbx.remoting.transport.socket;

import github.javaxbx.autoconfigure.SpringRpcProperties;
import github.javaxbx.config.RpcServiceConfig;
import github.javaxbx.provider.ServiceProvider;
import github.javaxbx.remoting.handler.RpcRequestHandler;
import github.javaxbx.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author xiaobiaoxu
 * @createTime 2022年03月10日 08:01:00
 */
@Slf4j
@Component
public class SocketRpcServer {

    private final ExecutorService threadPool;
    @Autowired
    private ServiceProvider serviceProvider;
    @Autowired
    private SpringRpcProperties springRpcProperties;
    @Autowired
    private RpcRequestHandler rpcRequestHandler;


    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
//        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, springRpcProperties.getPort()));
//            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket, rpcRequestHandler));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }

}
