package edu.cos398.trackingpixel.Providers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.cos398.trackingpixel.DAO.PixelDAO;
import edu.cos398.trackingpixel.Model.Pixel;
import edu.cos398.trackingpixel.Model.PixelVisit;

@Component
public class PixelProvider {
    @Autowired
    private PixelDAO dao;

    private Map<String, Integer> map = new HashMap<>();
    private Map<String, List<String>> campMap = new HashMap<>();

    public PixelProvider(){}

    public Pixel createNewPixel(String email, String campaign){
        Pixel p = dao.createPixel(email, campaign);
        return p;
    }

    public List<PixelVisit> getPixelVisitsForEmail(String campaign){
        List<Pixel> pixels = dao.getPixelsInCampaign(campaign);
        List<PixelVisit> pv = new ArrayList<>();

        for(Pixel p : pixels){
            pv.addAll(dao.getPixelVisit(p));
        }

        return pv;
    }

    public List<Pixel> getPixelsWithoutVisits(String campaign){
        List<Pixel> pixels = dao.getPixelsInCampaign(campaign);
        List<PixelVisit> pv = null;
        List<Pixel> retVal = new ArrayList<>();

        for(Pixel p : pixels){
            pv = dao.getPixelVisit(p);
            if(pv == null || pv.size() == 0){
                retVal.add(p);
            }
        }

        return retVal;
    }
}