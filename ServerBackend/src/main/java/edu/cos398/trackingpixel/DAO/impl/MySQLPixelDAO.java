package edu.cos398.trackingpixel.DAO.impl;

import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

import edu.cos398.trackingpixel.DAO.PixelDAO;
import edu.cos398.trackingpixel.Model.Pixel;
import edu.cos398.trackingpixel.Model.PixelVisit;

public class MySQLPixelDAO implements PixelDAO {
    private String mysql_username;
    private String mysql_pass;
    private String mysql_url;
    private String schema;

    public MySQLPixelDAO(String mysql_url, String schema, String mysql_username, String mysql_pass) throws Exception{
        this.schema = schema;
        this.mysql_pass = mysql_pass;
        this.mysql_username = mysql_username;
        this.mysql_url = mysql_url;
    }

    /**
     * Create a pixel object from result set
     */
    private Pixel createPixel(ResultSet rs) throws Exception{
        int id = rs.getInt("id");
        String campaign = rs.getString("campaign");
        String email = rs.getString("email");
        return new Pixel(id, email, campaign);
    }

    public Pixel getPixelById(int id){
        Pixel ret = null;
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "SELECT * FROM pixels WHERE id = ?; "
            );
            prep.setString(1, "" + id);
            ResultSet rs = prep.executeQuery();
            if(!rs.last()){
                ret = null;
            }else{
                ret = createPixel(rs);
            }

            rs.close();
            prep.close();
            con.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Set<String> getAllCampaigns(){
        Set<String> ret = new HashSet<>();
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "SELECT DISTINCT campaign FROM pixels;"
            );
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                String camp = rs.getString(("campaign"));
                ret.add(camp);
            }

            rs.close();
            prep.close();
            con.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    public List<PixelVisit> getPixelVisit(Pixel p){
        List<PixelVisit> ret = new ArrayList<>();
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "SELECT content FROM pixel_visits WHERE pixel_id = ?; "
            );
            prep.setString(1, "" + p.getId());
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                PixelVisit pv = new Gson().fromJson(rs.getString("content"), PixelVisit.class);
                ret.add(pv);
            }

            rs.close();
            prep.close();
            con.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Pixel> getPixelsInCampaign(String campaign){
        List<Pixel> ret = new ArrayList<>();
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "SELECT id, email, campaign FROM pixels WHERE campaign = ?;"
            );
            prep.setString(1, "" + campaign);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                Pixel pix = createPixel(rs);
                ret.add(pix);
            }

            rs.close();
            prep.close();
            con.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Pixel>();
        }
    }

    public void addPixelVisit(PixelVisit p){
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "INSERT into pixel_visits (pixel_id,`timestamp`, content) values(?, ?, ?); "
            );
            prep.setString(1, "" + p.getPixel().getId());
            prep.setString(2, "" + 0);
            prep.setString(3, "" + new Gson().toJson(p));
            prep.executeUpdate();

            prep.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pixel createPixel(String email, String campaign) {
        Pixel ret = null;
        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "INSERT into " +
                "pixels (email, campaign) " +
                "values(?, ?); "
            );
            prep.setString(1, "" + email);
            prep.setString(2, "" + campaign);
            prep.executeUpdate();
            prep.close();

            prep = con.prepareStatement(
                "SELECT id, email, campaign from pixels WHERE email = ? and campaign = ?; "
            );
            prep.setString(1, "" + email);
            prep.setString(2, "" + campaign);
            ResultSet rs = prep.executeQuery();
            
            if(!rs.last()){
                ret = null;
            }else{
                ret = createPixel(rs);
            }

            rs.close();
            prep.close();
            con.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}