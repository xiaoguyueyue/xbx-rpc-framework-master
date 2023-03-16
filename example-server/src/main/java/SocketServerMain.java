import github.javaxbx.HelloService;
import github.javaxbx.config.RpcServiceConfig;
import github.javaxbx.remoting.transport.socket.SocketRpcServer;
import github.javaxbx.serviceimpl.HelloServiceImpl;


public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}
