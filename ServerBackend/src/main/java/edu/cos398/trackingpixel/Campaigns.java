package edu.cos398.trackingpixel;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.Providers.*;

import edu.cos398.trackingpixel.DAO.*;
import edu.cos398.trackingpixel.Model.PixelVisit;

@RestController
@CrossOrigin
public class Campaigns {    
    @Autowired
    private PixelProvider pixelProvider;

    @Autowired
    private AuthProvider ap;

    @Autowired
    private PixelDAO dao;

    @RequestMapping(value = "/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getAllCamps(){
        return dao.getAllCampaigns();
    }

    @RequestMapping(value = "/campaigns/{campID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PixelVisit> getVisits(@PathVariable String campID, HttpServletRequest req, HttpServletResponse resp){
        if(!ap.isLoggedIn(req.getSession(true))){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return pixelProvider.getPixelVisitsForEmail(campID);
    }
}   