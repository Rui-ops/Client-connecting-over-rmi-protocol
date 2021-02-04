package email;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EMailRMI extends Remote {
    
    public String FirstMail(String username, String password)throws RemoteException;
}
