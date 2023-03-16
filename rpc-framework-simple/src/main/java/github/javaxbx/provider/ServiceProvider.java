package github.javaxbx.provider;

import github.javaxbx.config.RpcServiceConfig;

/**
 * store and provide service object.
 *
 * @author xioabiaoxu
 * @createTime 2023年02月24日 16:52:00
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc service name
     * @return service object
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

    /**
     * 服务是否为空
     */
    boolean isEmpty();

}
