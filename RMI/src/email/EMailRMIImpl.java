package email;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import javax.mail.*;
import java.io.*;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.IMAPFolder;
import javax.activation.*;

public class EMailRMIImpl extends UnicastRemoteObject implements EMailRMI {
    
//    String message = "";

    public EMailRMIImpl() throws RemoteException {
    }
    @Override
    public String FirstMail(String username, String password) throws RemoteException {
        String message = "";
        String protocol = "imap";
        String host = "webmail.kth.se";
        try{
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", protocol);
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.auth", "true");
            props.setProperty("mail.imap.ssl.enable", "true");
            Session session = Session.getInstance(props);
            IMAPStore store = (IMAPStore)session.getStore(protocol);
            store.connect(host, username, password);
            IMAPFolder inbox = (IMAPFolder)store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            IMAPMessage firstMessage = (IMAPMessage)inbox.getMessage(inbox.getMessageCount()-1);
            
            String subject = firstMessage.getSubject();
            Address from = (Address)firstMessage.getFrom()[0];
            message = (String)firstMessage.getContent();
        }catch(NoSuchProviderException e){
            e.printStackTrace();
        }catch(MessagingException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return message;
    }
}
