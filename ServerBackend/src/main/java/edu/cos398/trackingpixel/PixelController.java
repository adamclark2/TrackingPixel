package edu.cos398.trackingpixel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.DAO.PixelDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import edu.cos398.trackingpixel.Model.*;

@RestController
public class PixelController {

    @Autowired
    private PixelDAO pixelDatabase;

    @RequestMapping(path = "/pixels/{pixelID}", produces = "text/plain")
    public String index(HttpServletRequest req, @PathVariable int pixelID, HttpServletResponse resp){
        try{
            Pixel p = pixelDatabase.getPixelById(pixelID);
            if(p == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return "Pixel not found";
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

            PixelVisit pv = new PixelVisit(headers, cookies, p);
            pixelDatabase.addPixelVisit(pv);

            Cookie c = new Cookie("HasVisited", "True");
            resp.addCookie(c);

            Gson g = new Gson();
            return "(TODO) Pixel goes here:\n";
        } catch(Exception e){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintWriter pr = new PrintWriter(bos);
            e.printStackTrace(pr);
            pr.flush();

            String s = bos.toString();
            try{
                pr.close();
                bos.close();
            }catch (Exception ee){
                s += "\n\n An error occured while closing the print writer!\n";
            }
            


            return "An exception happened: \nMessage: " + e.getMessage() + "\nLocalized Message: " + e.getLocalizedMessage() + "\n\nStackTrace: \n" + s;
        }
    }

    @RequestMapping(path = "/pixelInfo/{pixelID}", produces = "application/json")
    public List<PixelVisit> pixelInfo(HttpServletRequest req, @PathVariable int pixelID, HttpServletResponse resp){
        Pixel p = pixelDatabase.getPixelById(pixelID);
        List<PixelVisit> pv =  pixelDatabase.getPixelVisit(p);

        return pv;
    }

}
