package github.javaxbx.spring;

import github.javaxbx.provider.ServiceProvider;
import github.javaxbx.remoting.transport.netty.server.NettyRpcServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月02日 14:07
 */
@Component
public class CustomApplicationListener implements ApplicationRunner, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    @Qualifier("zkServiceProviderImpl")
    private ServiceProvider serviceProvider;

    public CustomApplicationListener() {
//        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!serviceProvider.isEmpty()) {
            NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
            nettyRpcServer.start();
        }
    }
}