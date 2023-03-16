package github.javaxbx.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月15日 15:23
 */
@Configuration
@EnableConfigurationProperties({SpringRpcProperties.class})
public class RpcAutoConfiguration {
}
