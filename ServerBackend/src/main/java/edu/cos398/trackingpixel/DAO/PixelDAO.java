package edu.cos398.trackingpixel.DAO;

import edu.cos398.trackingpixel.Model.*;
import java.util.*;

public interface PixelDAO{
    public Pixel getPixelById(int id);
    public Set<String> getAllCampaigns();

    public List<PixelVisit> getPixelVisit(Pixel p);
    public List<Pixel> getPixelsInCampaign(String campaign);

    public void addPixelVisit(PixelVisit p);
    public Pixel createPixel(String email, String campaign) ;
}