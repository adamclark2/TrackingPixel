package edu.cos398.trackingpixel.Model;

import java.util.*;

import com.google.gson.annotations.Expose;

public class PixelVisit{
    @Expose
    private Map<String, String> headers = new HashMap<>();

    @Expose
    private Map<String, String> cookies = new HashMap<>();

    @Expose
    private long timestamp = System.currentTimeMillis();

    @Expose
    private Pixel pixel;

    @Expose
    private UserMetadata metadata;

    public PixelVisit(){}
    public PixelVisit(Map<String, String> headers, Map<String, String> cookies, Pixel p){
        this.headers = headers;
        this.cookies = cookies;
        this.pixel = p;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public Pixel getPixel(){
        return pixel;
    }

    public void setUserMetadata(UserMetadata metadata){
        this.metadata = metadata;
    }
}