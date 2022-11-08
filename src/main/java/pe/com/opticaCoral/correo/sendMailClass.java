package pe.com.opticaCoral.correo;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMailClass {

    private Session m_Session;
    private Message m_simpleMessage;
    private InternetAddress m_fromAddress;
    private InternetAddress m_toAddress;
    private Properties m_properties;

    public void sendMail(String mail) throws Exception {
        try {

            m_properties = new Properties();
            m_properties.put("mail.smtp.host", "smtp.gmail.com");
            m_properties.put("mail.smtp.socketFactory.port", "465");
            m_properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            m_properties.put("mail.smtp.auth", "true");
            m_properties.put("mail.smtp.port", "465");
            m_properties.put("mail.debug", "false");
            m_properties.put("mail.smtp.ssl.enable", "true");

            m_Session = Session.getDefaultInstance(m_properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("davidpachecoask@gmail.com", "Uatr1ceaT"); // username and the password 
                }

            });

            m_properties = new Properties();
            m_properties.put("mail.smtps.auth", "true");
            m_properties.put("mail.debug", "true");

            m_simpleMessage = new MimeMessage(m_Session);

            m_fromAddress = new InternetAddress("davidpachecoask@gmail.com");
            m_toAddress = new InternetAddress(mail);

            m_simpleMessage.setFrom(m_fromAddress);
            m_simpleMessage.setRecipient(RecipientType.TO, m_toAddress);
            m_simpleMessage.setSubject("Test letter");
            m_simpleMessage.setContent("Hi, this is test letter.", "text/plain");

            Transport.send(m_simpleMessage);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        sendMailClass send = new sendMailClass();
        try {
            send.sendMail("indicedepublicaciones@gmail.com");
        } catch (Exception ex) {
            Logger.getLogger(sendMailClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
