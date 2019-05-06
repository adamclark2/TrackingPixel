package edu.cos398.trackingpixel;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.DAO.PixelDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import edu.cos398.trackingpixel.Model.*;
import edu.cos398.trackingpixel.Providers.UserMetadataProvider;

@RestController
public class PixelController {

    @Autowired
    private PixelDAO pixelDatabase;

    @Autowired
    private UserMetadataProvider ump;

    @RequestMapping(path = "/pixels/{pixelID}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] index(HttpServletRequest req, @PathVariable int pixelID, HttpServletResponse resp)throws Exception {
        Pixel p = pixelDatabase.getPixelById(pixelID);
        if(p == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return generatePixel();
        }

        Map<String, String> headers = new HashMap<>();
        Iterator <String> i = req.getHeaderNames().asIterator();
        while(i.hasNext()){
            String header = i.next();
            headers.put(header, req.getHeader(header));
        }

        Map<String, String> cookies = new HashMap<>();
        if(req.getCookies() != null){
            for(Cookie c : req.getCookies()){
                cookies.put(c.getName(), c.getValue());
            }
        }

        String useragent = headers.get("user-agent");
        if(useragent == null || useragent == ""){
            useragent = headers.get("User-Agent");
            if(useragent == null || useragent == ""){
                useragent = headers.get("UserAgent");
                if(useragent == null || useragent == ""){
                    useragent = headers.get("useragent");
                }
            }
        }

        String ipAddress = req.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
          ipAddress = req.getRemoteAddr();  
        }
        PixelVisit pv = new PixelVisit(headers, cookies, p);
        pv.setUserMetadata(ump.getUserMetadata(useragent, ipAddress));
        pixelDatabase.addPixelVisit(pv);

        Cookie c = new Cookie("HasVisited", "True");
        resp.addCookie(c);

        return generatePixel();
    }

    @RequestMapping(path = "/pixelInfo/{pixelID}", produces = "application/json")
    public List<PixelVisit> pixelInfo(HttpServletRequest req, @PathVariable int pixelID, HttpServletResponse resp){
        Pixel p = pixelDatabase.getPixelById(pixelID);
        List<PixelVisit> pv =  pixelDatabase.getPixelVisit(p);

        return pv;
    }

    public byte[] generatePixel(){
        BufferedImage bi = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 400, 400);

        g.setColor(Color.blue);
        Font arial = new Font("Arial", Font.BOLD, 28);
        g.setFont(arial);
        g.drawChars("Tracking Pixel".toCharArray(),0, "Tracking Pixel".length(), 400/4, 400/2);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(50000);
        try{
            ImageIO.write(bi, "png", bos);
        }catch(Exception e){
            return null;
        }
        
        return bos.toByteArray();
    }

}
