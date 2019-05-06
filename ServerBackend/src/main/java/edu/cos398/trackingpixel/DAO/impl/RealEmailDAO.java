package edu.cos398.trackingpixel.DAO.impl;

import java.util.*;

import javax.mail.Session;

import edu.cos398.trackingpixel.DAO.EmailDAO;
import edu.cos398.trackingpixel.Model.EmailCampaign;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.*;

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

public class RealEmailDAO implements EmailDAO{
    private String username;
    private String password;
    private String smtp;
    private String smtpAuth;
    private String starttls;
    private String smtpHost;
    private String smtpPort;

    private PixelProvider pixelProvider;

    private String hostAddress;

    public RealEmailDAO(
         String username,
         String password,
         String smtp,
         String smtpAuth,
         String starttls,
         String smtpHost,
         String smtpPort,

         PixelProvider pixelProvider,
         String hostAddress
    ){
        this.username = username;
        this.password = password;
        this.smtp = smtp;
        this.smtpAuth = smtpAuth;
        this.starttls = starttls;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;

        this.pixelProvider = pixelProvider;
        this.hostAddress = hostAddress;
    }

    public List<String> sendEmail(EmailCampaign camp){
        List<String> badEmails = new ArrayList<>();
        for(String to : camp.getReceivers()){
            try{
                Properties props = new Properties();
                props.put("mail.smtp.auth", smtpAuth + "");
                props.put("mail.smtp.starttls.enable", starttls + "");
                props.put("mail.smtp.host", smtpHost + "");
                props.put("mail.smtp.port", smtpPort + "");
    
                Session session= Session.getInstance(props,
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
                Pixel p = pixelProvider.createNewPixel(to, camp.getSubject());
                message.setContent(
                    camp.getContent() + 
                    "<br><br><br><br><img src=\'" + hostAddress + "/pixels/" + p.getId() + ".png\'>", "text/html; charset=utf-8"
                );
    
                Transport.send(message);
    
                }catch (Exception e){
                    badEmails.add(to);
                    e.printStackTrace();
                }
        }

        return badEmails;
        
    }
}