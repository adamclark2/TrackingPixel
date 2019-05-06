package edu.cos398.trackingpixel.DAO.impl;

import edu.cos398.trackingpixel.DAO.PixelDAO;
import edu.cos398.trackingpixel.Model.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.stereotype.Component;

@Component
public class InMemoryPixelDAO implements PixelDAO {

    private List<Pixel> pixels = new ArrayList<>();
    private List<PixelVisit> visits = new ArrayList<>();
    private Integer maxId = 10;

    @Override
    public Pixel getPixelById(int id){
        for(Pixel p : pixels){
            if(p.getId() == id){
                return p;
            }
        }

        return null;
    }

    @Override
    public List<PixelVisit> getAllPixelVisits(){
        return visits;
    }

    @Override
    public void addPixelVisit(PixelVisit p){
        synchronized(visits){
            visits.add(p);
        }
    }

    public InMemoryPixelDAO(){
    }

    public List<PixelVisit> getPixelVisit(Pixel p){
        List<PixelVisit> retVal = new ArrayList<>();
        synchronized(visits){
            for(PixelVisit pv : visits){
                if(pv.getPixel().getId() == p.getId()){
                    retVal.add(pv);
                }
            }
        }

        return retVal;
    }

    public List<Pixel> getPixelsInCampaign(String campaign){
        List<Pixel> ret = new ArrayList<Pixel>();
        for(Pixel p : pixels){
            if(p.getCampaign().equals(campaign)){
                ret.add(p);
            }
        }
        return ret;
    }

    public Set<String> getAllCampaigns(){
        Map<String, String> camps = new HashMap<>();
        for(Pixel p : pixels){
            if(!camps.containsKey(p.getCampaign())){
                camps.put(p.getCampaign(), "");
            }
        }

        return camps.keySet();
    }

    public Pixel createPixel(String email, String campaign) {
        Pixel p;
        synchronized (maxId){
            maxId++;
            p = new Pixel(maxId, email, campaign);
            pixels.add(p);
        }

        return p;
    }
}