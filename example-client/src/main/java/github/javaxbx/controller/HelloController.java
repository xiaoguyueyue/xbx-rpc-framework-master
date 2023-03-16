package github.javaxbx.controller;

import github.javaxbx.Hello;
import github.javaxbx.HelloService;
import github.javaxbx.annotation.RpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    @GetMapping("/get")
    public List<String> test() throws InterruptedException {
        List<String> list = new ArrayList<>();
        String hello = this.helloService.hello(new Hello("测试", "222"));
        //如需使用 assert 断言，需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        list.add(hello);
        list.add(helloService.hello(new Hello("成功", "222")));
        return list;
    }
}
