/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RMIServer {
    /**
     * 发布接口
     * @param args
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        /**
         * 创建一个远程接口的实现类
         */
        IHelloRMI helloRMI=new HelloRMIImpl();
        /**
         * 创建一个RMI端口，如果没有创建，RMI服务也就无法绑定到这个接口上面
         */
        System.out.println("正在创建接口");
        LocateRegistry.createRegistry(8889);
        System.out.println("创建端口成功");
        /**
         * 在这里声明一下三个异常
         * RemoteException:创建远程对象异常
         * AlreadyBoundException:重复绑定异常
         * MalformedURLException:指定的URI发生错误的异常
         */
        System.out.println("正在绑定");
        Naming.bind("rmi://localhost:8889/HelloRMI",helloRMI);
        System.out.println("绑定成功！");
        //注意，何以省略掉协议前缀，即使rmi:
        //Naming.bind("//localhost:8889/HelloRMI",helloRMI);
    }
}
