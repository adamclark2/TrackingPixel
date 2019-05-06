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

public class MockEmailDAO implements EmailDAO{

    private PixelProvider pixelProvider;
    private String hostAddress;

    public MockEmailDAO(PixelProvider pixelProvider){
        this.pixelProvider = pixelProvider;
    }

    public List<String> sendEmail(EmailCampaign camp){
        List<String> badEmails = new ArrayList<>();
        for(String to : camp.getReceivers()){
            Pixel p = pixelProvider.createNewPixel(to, camp.getSubject());
        }

        return badEmails;
    }
}