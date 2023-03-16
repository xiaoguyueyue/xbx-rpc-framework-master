package github.javaxbx.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记作为SPI扩展点的接口，可以有多个内置或用户定义的实现，运行时通过配置找到具体的实现类
 * 扩展点class必须是接口，且被@SPI注解修饰，这是两个必要条件
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
}
