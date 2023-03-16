package github.javaxbx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.registry"),
    LOADBALANCE("rpc.loadbalance"),
    PORT("rpc.port"),
    SERIALIZER("rpc.serializer");

    private final String propertyValue;

}
