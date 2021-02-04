package email;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        
        String username = "ruizhi";
        String password = "yrz.547166287";
        
        EMailRMI emailRMI = (EMailRMI) Naming.lookup("rmi://localhost:8889/EMailRMI");
        String s = emailRMI.FirstMail(username, password);
        System.out.println(s);
    }
}