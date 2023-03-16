package github.javaxbx;

import github.javaxbx.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xbx
 * @createTime 2022年03月10日 07:52:00
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class DemoRpcServiceImpl implements github.javaxbx.DemoRpcService {

    @Override
    public String hello() {
        return "hello";
    }
}
