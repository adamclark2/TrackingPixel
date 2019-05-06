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

import edu.cos398.trackingpixel.DAO.EmailDAO;
import edu.cos398.trackingpixel.Model.EmailCampaign;
import edu.cos398.trackingpixel.Model.Pixel;
import edu.cos398.trackingpixel.Providers.*;

@RestController
public class EmailSender{



    @Autowired
    private PixelProvider pixelProvider;

    @Autowired
    private AuthProvider ap;

    @Autowired
    private EmailDAO email;

   

    /**
     * @throws Exception
     */
    @RequestMapping(value= "/emailSender", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public List<String> sendEmail(HttpServletRequest req, HttpServletResponse resp, @RequestBody EmailCampaign camp) throws Exception{
        if(!ap.isLoggedIn(req.getSession(true))){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return email.sendEmail(camp);
    }
}
