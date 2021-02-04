package mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPFolder;
import javax.activation.*;

/**
 * @author kp
 */
public class ReceiveMails {

    private static final String TRUE_SYMBOL_Y = "y";

    private static final String MENU_SEND_OPTION = "1";
    private static final String MENU_RECEIVE_OPTION = "2";
    private static final String MENU_EXIT_OPTION = "3";

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        while (true) {
            try {
                System.out.println("\r\n====================== MENU ========================");
                System.out.println("    (1)send        (2)receive        (3)exit");
                System.out.println("====================================================");
                System.out.print("Input option: ");
                String option = reader.readLine();

                if (MENU_SEND_OPTION.equalsIgnoreCase(option)) {
                    send();
                } else if (MENU_RECEIVE_OPTION.equalsIgnoreCase(option)) {
                    receive();
                } else if (MENU_EXIT_OPTION.equalsIgnoreCase(option)) {
                    // 退出
                    break;
                }

                System.out.flush();
            } catch (Exception e) {
                System.out.println("I/O exception");
                e.printStackTrace();
            }
        }

        System.out.println("\r\nEND!");
    }

    private static void send() throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Please input smtp host: ");
        String host = reader.readLine();
        System.out.print("Please input smtp port: ");
        String port = reader.readLine();
        System.out.print("Please input email address: ");
        String email = reader.readLine();
        System.out.print("Please input email password: ");
        String password = reader.readLine();
        System.out.print("Is SSL(y/n): ");
        String isSsl = reader.readLine();
        String isStartTls = "";
        if (!TRUE_SYMBOL_Y.equalsIgnoreCase(isSsl)) {
            System.out.print("Is STARTTLS(y/n): ");
            isStartTls = reader.readLine();
        }
        System.out.println("################### Message ########################");
        System.out.print("Please input receiver: ");
        String receiver = reader.readLine();
        System.out.print("Please input subject: ");
        String subject = reader.readLine();
        System.out.print("Please input content: ");
        String content = reader.readLine();
        System.out.println("##################### END ##########################");

        // 配置连接参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.timeout", "20000");
        props.setProperty("mail.smtp.connectiontimeout", "20000");
        // 此处对于 SSL 和 STARTTLS 的处理不当，有待改正
        if (TRUE_SYMBOL_Y.equalsIgnoreCase(isSsl)) {
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.ssl.enable", "true");
        } else if (TRUE_SYMBOL_Y.equalsIgnoreCase(isStartTls)) {
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
        }

        try {
            // 获取会话
            Session session = Session.getInstance(props);

            // 构建邮件信息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            // 设置内容
            Multipart contentPart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html;charset=UTF-8");
            contentPart.addBodyPart(bodyPart);
            message.setContent(contentPart);

            // 建立连接并发送
            Transport transport = session.getTransport();
            transport.connect(email, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("********************** Result **********************");
            System.out.println("send success!");
            System.out.println("****************************************************");
        } catch (Exception e) {
            System.out.println("********************** Result **********************");
            System.out.println("send fail!");
            System.out.println("****************************************************");
            e.printStackTrace();
        }
    }

    private static void receive() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Please input imap host: ");
        String host=br.readLine();
        System.out.print("Please input imap port: ");
        String port=br.readLine();
        System.out.print("Please input email address: ");
        String email =br.readLine();
        System.out.print("Please input email password: ");
        String password =br.readLine();
        System.out.print("Is SSL(y/n): ");
        String isSsl = br.readLine();

        // 构建基本的配置信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.timeout", "20000");
        props.setProperty("mail.imap.connectiontimeout", "20000");
        if (TRUE_SYMBOL_Y.equalsIgnoreCase(isSsl)) {
            props.setProperty("mail.imap.auth", "true");
            props.setProperty("mail.imap.ssl.enable", "true");
        }

        try {
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore();
            store.connect("webmail.kth.se",email, password);

            IMAPFolder folder = (IMAPFolder)store.getFolder("inbox");
            if (folder.isOpen()) {
                System.out.println("The folder is opened");
            } else {
                System.out.println("Open the folder");
                folder.open(Folder.READ_ONLY);
            }

            System.out.println("********************** Result **********************");
            System.out.println("receive success!");
            System.out.println(folder.getFullName());
            System.out.println("total email count: " + folder.getMessageCount());
            System.out.println("unread email count: " + folder.getUnreadMessageCount());
            System.out.println("deleted email count: " + folder.getDeletedMessageCount());
            System.out.println("new email count: " + folder.getNewMessageCount());
            Message[] messages = folder.getMessages();
            IMAPMessage msg = (IMAPMessage) messages[folder.getMessageCount()-1];
            System.out.println("content: " + msg.getContent());
            System.out.println("****************************************************");
        } catch (Exception e) {
            System.out.println("********************** Result **********************");
            System.out.println("receive fail!");
            System.out.println("****************************************************");
            e.printStackTrace();
        }
    }
}