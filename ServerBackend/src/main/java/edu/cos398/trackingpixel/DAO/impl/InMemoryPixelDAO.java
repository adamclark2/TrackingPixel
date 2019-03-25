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
    public void addPixel(Pixel p){
        synchronized(pixels){
            pixels.add(p);
        }
    }

    @Override
    public void addPixelVisit(PixelVisit p){
        synchronized(visits){
            visits.add(p);
        }
    }

    public InMemoryPixelDAO(){
        String[] cat = {"Awesome", "Cool", "Wow"};
        pixels.add(new Pixel(0, Arrays.asList(cat)));
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
}