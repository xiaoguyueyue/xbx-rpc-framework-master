package github.javaxbx.remoting.transport;

import github.javaxbx.extension.SPI;
import github.javaxbx.remoting.dto.RpcRequest;

/**
 * send RpcRequest。
 *
 * @author xbx
 * @createTime 2022年02月29日 13:26:00
 */
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
