
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Administrator on 2016/4/20.
 */
public class HelloRMIImpl extends UnicastRemoteObject implements IHelloRMI{

    /*i
     * 这里必须是构造器抛出RemoteException
     * 因为继承父类UnicastRemoteObject构造器中抛出RemoteException
     * 同时这个类是必须继承UnicastRemoteObject这个类
     */
    public HelloRMIImpl()throws RemoteException{
    }
    @Override
    public String sayHelloToClient()throws RemoteException {
        return "你好客户端，我是服务端";
    }
}