package github.javaxbx.remoting.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiaobiaoxu
 * @createTime 2022年10月2日 12:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {

    /**
     * rpc message type
     */
    private byte messageType;
    /**
     * serialization type
     */
    private byte codec;
    /**
     * compress type
     */
    private byte compress;
    /**
     * request id
     * 请求和返回都同一个id，便于用户收到响应后知道要回填给哪一个线程任务
     */
    private int requestId;
    /**
     * request data
     */
    private Object data;

}
