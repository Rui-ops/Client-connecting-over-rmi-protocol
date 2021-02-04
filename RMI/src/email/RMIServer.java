package email;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        
        
        EMailRMI emailRMI=new EMailRMIImpl();
       
        System.out.println("Creating port.");
        LocateRegistry.createRegistry(8889);
        System.out.println("Port created.");
        
        System.out.println("Binding.");
        Naming.bind("rmi://localhost:8889/EMailRMI",emailRMI);
        System.out.println("Binding succeeded");
        
    }
}
