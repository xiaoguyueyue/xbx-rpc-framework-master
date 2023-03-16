package github;

import github.javaxbx.annotation.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RpcScan(basePackage = {"github.javaxbx"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);

        SpringApplication springApplication = new SpringApplication(NettyServerMain.class);
        springApplication.run(args);
//        NettyRpcServer nettyRpcServer = (NettyRpcServer) springApplication.getBean("nettyRpcServer");
//        ProviderConfig providerConfig = (ProviderConfig) springApplication.getBean("providerConfig");
//        // Register service manually
//        HelloService helloService2 = new HelloServiceImpl2();
//        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                .group("test2").version("version2").service(helloService2).build();
//        nettyRpcServer.registerService(rpcServiceConfig);
//        System.out.println("行不行不行" + providerConfig.getZookeeper());
//        nettyRpcServer.start();
    }
}
