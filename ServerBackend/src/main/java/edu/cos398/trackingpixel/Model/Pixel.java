package edu.cos398.trackingpixel.Model;

import java.util.*;

import com.google.gson.annotations.Expose;

public class Pixel{
    @Expose
    private int id;

    @Expose
    private String email;

    @Expose
    private String campaign;


    public int getId(){
        return id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getCampaign(){
        return campaign;
    }

    public Pixel(){}
    public Pixel(int id, String email, String campaign){
        this.id = id;
        this.email = email;
        this.campaign = campaign;
    }
}