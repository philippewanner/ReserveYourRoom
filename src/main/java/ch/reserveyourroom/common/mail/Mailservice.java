package ch.reserveyourroom.common.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by Danijel
 */
public class Mailservice {

    //private static final String SMTP_HOST = "smtp.bfh.ch";
    private static final String SMTP_HOST = "mail.gmx.net";
    private static final int SMTP_PORT = 587;

    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public static void sendMail(String recipient,
                                String subject,
                                String msg) throws EmailException
    {
        System.out.println("Entering sendMail-method");
        SimpleEmail email = new SimpleEmail();
        //HtmlEmail email = new HtmlEmail();
        email.setDebug(true);

        email.setHostName(SMTP_HOST);
        //email.setSslSmtpPort("587");
        email.setSmtpPort(465);

        email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        email.setSSLOnConnect(true);
        //email.setStartTLSEnabled(true);

        email.setFrom(USERNAME, "RYR Responder");
        email.setSubject(subject);
        email.setMsg(msg);
        email.addTo(recipient);

        email.send();
        System.out.println("mail send to "+recipient);
    }

}