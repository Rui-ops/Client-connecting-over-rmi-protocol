/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2016/4/20.
 * Java RMI客户端
 */
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        /**
         * IHelloRMI接口从服务端拷贝过来即可
         * 这里必须注意的是IHelloRMI的包路径必须与服务端一致
         * 如果不一致，会发生ClassNotFoundException
         * Naming.lookup是在指定地址插在rmi服务
         */
        IHelloRMI helloRMI = (IHelloRMI) Naming.lookup("rmi://localhost:8889/HelloRMI");
        String s = helloRMI.sayHelloToClient();
        System.out.println(s);
    }
}
