package github.javaxbx.registry;

import github.javaxbx.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service registration
 *
 * @author xiaobiaoxu
 * @createTime 2022年03月13日 08:39:00
 */
@SPI
public interface ServiceRegistry {
    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}
