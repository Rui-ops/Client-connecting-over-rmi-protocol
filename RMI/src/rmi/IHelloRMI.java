
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IHelloRMI extends Remote {

    public String sayHelloToClient()throws RemoteException;
}
