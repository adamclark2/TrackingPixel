package edu.cos398.trackingpixel.DAO.impl;

import java.sql.*;

import edu.cos398.trackingpixel.DAO.AuthDAO;

public class MySQLAuthDAO implements AuthDAO{
    private String mysql_username;
    private String mysql_pass;
    private String mysql_url;
    private String schema;

    public MySQLAuthDAO(String mysql_url, String schema, String mysql_username, String mysql_pass) throws Exception{
        Class.forName("com.mysql.jdbc.Driver"); 

        this.schema = schema;
        this.mysql_pass = mysql_pass;
        this.mysql_username = mysql_username;
        this.mysql_url = mysql_url;
    }

    public boolean auth(String username, String password){
        boolean ret = false;

        try{
            Connection con=DriverManager.getConnection(mysql_url + "/" + schema, mysql_username,mysql_pass);
            PreparedStatement prep = con.prepareStatement(
                "SELECT * " + 
                "FROM users " +
                "WHERE username = ? AND password=sha2(?, 224); "
            );
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            if(!rs.last()){
                ret = false;
            }else{
                ret = true;
            }

            rs.close();
            prep.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


        return ret;
    }
}