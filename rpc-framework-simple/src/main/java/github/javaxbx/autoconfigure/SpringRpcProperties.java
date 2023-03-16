package github.javaxbx.autoconfigure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月14日 15:48
 */
@Data
@ConfigurationProperties(prefix = "spring.rpc")
public class SpringRpcProperties {
    private String registry = "127.0.0.1:2181";
    private Integer port = 9998;
    private String serializer;
    @NestedConfigurationProperty
    private ClientProperties client = new ClientProperties();
    @NestedConfigurationProperty
    private ServiceProperties service = new ServiceProperties();
}
