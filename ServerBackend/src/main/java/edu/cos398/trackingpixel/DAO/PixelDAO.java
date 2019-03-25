package edu.cos398.trackingpixel.DAO;

import edu.cos398.trackingpixel.Model.*;
import java.util.*;

public interface PixelDAO{
    Pixel getPixelById(int id);
    List<PixelVisit> getAllPixelVisits();
    void addPixel(Pixel p);
    void addPixelVisit(PixelVisit p);
    List<PixelVisit> getPixelVisit(Pixel p);
}