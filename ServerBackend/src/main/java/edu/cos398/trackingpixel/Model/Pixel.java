package edu.cos398.trackingpixel.Model;

import java.util.*;

import com.google.gson.annotations.Expose;

public class Pixel{
    @Expose
    private int id;

    @Expose
    private List<String> categories = new ArrayList<>();

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<String> getCategories(){
        return this.categories;
    }

    public void addCategory(String categoryName){
        this.categories.add(categoryName);
    }

    public Pixel(){}
    public Pixel(int id, List<String> categories){
        this.id = id;
        this.categories = categories;
    }
}