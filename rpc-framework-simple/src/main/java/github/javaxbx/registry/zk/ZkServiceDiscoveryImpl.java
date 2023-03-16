package github.javaxbx.registry.zk;

import github.javaxbx.autoconfigure.SpringRpcProperties;
import github.javaxbx.enums.RpcConfigEnum;
import github.javaxbx.enums.RpcErrorMessageEnum;
import github.javaxbx.exception.RpcException;
import github.javaxbx.extension.ExtensionLoader;
import github.javaxbx.loadbalance.LoadBalance;
import github.javaxbx.registry.ServiceDiscovery;
import github.javaxbx.registry.zk.util.CuratorUtils;
import github.javaxbx.remoting.dto.RpcRequest;
import github.javaxbx.utils.CollectionUtil;
import github.javaxbx.utils.CustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;

/**
 * service discovery based on zookeeper
 *
 * @Author xiaobiaoxu
 * @Date 2023年03月14日 15:48
 */
@Slf4j
@Component("zkServiceDiscoveryImpl")
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;
    private SpringRpcProperties springRpcProperties;
    public ZkServiceDiscoveryImpl(SpringRpcProperties springRpcProperties) {
        this.springRpcProperties = springRpcProperties;
        Properties properties = CustomConfig.getProperties();
        String loadbalance = properties != null && properties.getProperty(RpcConfigEnum.LOADBALANCE.getPropertyValue()) != null
                ? properties.getProperty(RpcConfigEnum.LOADBALANCE.getPropertyValue()) : springRpcProperties.getClient().getLoadBalance();
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(loadbalance);
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}