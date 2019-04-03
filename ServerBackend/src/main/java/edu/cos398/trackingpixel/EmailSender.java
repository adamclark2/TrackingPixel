package edu.cos398.trackingpixel;

import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSender{

    /**
     * Proof of conscept
     * 
     * If HTTPS isn't enabled on the server your password will be sent over
     * plaintext to Spring
     * 
     * Spring communicating with gmail should be start-tls
     * 
     * Fortunately gmail has decided to use a proxy so we can only tell if a user
     * opened the email. Good for them.
     * @throws Exception
     */
    @RequestMapping(value= "/emailSender", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sendEmail(HttpServletRequest req) throws Exception{
        final String username = "adam.clark2@maine.edu";

        Scanner sc = new Scanner(System.in);
        System.out.println("Password: \n");
        final String password = req.getHeader("password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(username));
            message.setSubject("Testing Subject 2");
            message.setContent("<img src=\'http://localhost:8080/pixelEmailImage/100.png\'>", "text/html; charset=utf-8");

            Transport.send(message);

            return "Done";

        } catch (MessagingException e) {
            return "ERROR";
        }
    }
}
