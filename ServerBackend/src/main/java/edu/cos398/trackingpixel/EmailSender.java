package edu.cos398.trackingpixel;

import java.util.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.Model.EmailCampaign;
import edu.cos398.trackingpixel.Model.Pixel;
import edu.cos398.trackingpixel.Providers.*;

@RestController
public class EmailSender{

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.smtp}")
    private String smtp;

    @Value("${email.smtp.auth}")
    private String smtpAuth;

    @Value("${email.smtp.starttls.enable}")
    private String starttls;

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private String smtpPort;

    @Autowired
    private PixelProvider pixelProvider;

    @Autowired
    private AuthProvider ap;

    @Value("${server.hostAddress}")
    private String hostAddress;

    /**
     * @throws Exception
     */
    @RequestMapping(value= "/emailSender", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public List<String> sendEmail(HttpServletRequest req, HttpServletResponse resp, @RequestBody EmailCampaign camp) throws Exception{
        if(!ap.isLoggedIn(req.getSession(true))){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        List<String> badEmails = new ArrayList();
        for(String to : camp.getReceivers()){
            try{
            Properties props = new Properties();
            props.put("mail.smtp.auth", smtpAuth + "");
            props.put("mail.smtp.starttls.enable", starttls + "");
            props.put("mail.smtp.host", smtpHost + "");
            props.put("mail.smtp.port", smtpPort + "");

            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username + "", password + "");
                }
                });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            message.setSubject(camp.getSubject());
            message.setContent(camp.getContent(), "text/html; charset=utf-8");
            Pixel p = pixelProvider.createNewPixel(to, camp.getSubject());

            message.setContent("<img src=\'" + hostAddress + "/pixels/" + p.getId() + ".png\'>", "text/html; charset=utf-8");

            Transport.send(message);
            }catch (Exception e){
                badEmails.add(to);
                e.printStackTrace();
            }
        }

        return badEmails;
    }
}
