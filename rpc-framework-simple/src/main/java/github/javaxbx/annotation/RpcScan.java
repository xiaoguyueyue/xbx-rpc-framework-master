package github.javaxbx.annotation;

import github.javaxbx.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * scan custom annotations
 * (客户端/服务器)注释在启动类上，启动RPC工具，扫描该项目相关的注解，并处理
 *
 * @author xiaobiaoxu
 * @createTime 2022年03月10日 21:42:00
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {

    String[] basePackage() default {};

}
