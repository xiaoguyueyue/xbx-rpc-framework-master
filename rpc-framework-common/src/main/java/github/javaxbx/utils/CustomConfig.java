package github.javaxbx.utils;

import github.javaxbx.enums.RpcConfigEnum;

import java.util.Properties;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月02日 16:26
 */
public class CustomConfig {
    private static volatile Properties properties;
    public static Properties getProperties() {
        if (properties == null) {
            synchronized (CustomConfig.class) {
                if (properties == null) {
                    properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());
                }
            }
        }
        return properties;
    }
}
