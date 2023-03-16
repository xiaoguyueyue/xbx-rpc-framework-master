package github.javaxbx;

import github.javaxbx.annotation.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 客户端(Web请求)
 */
@RpcScan(basePackage = {"github.javaxbx"})
@SpringBootApplication
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication springApplication = new SpringApplication(NettyClientMain.class);
        springApplication.run(args);
    }
}
