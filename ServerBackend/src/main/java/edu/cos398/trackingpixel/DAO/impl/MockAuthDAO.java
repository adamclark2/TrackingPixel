package edu.cos398.trackingpixel.DAO.impl;

import edu.cos398.trackingpixel.DAO.AuthDAO;

public class MockAuthDAO implements AuthDAO {
    public boolean auth(String username, String password){
        return true;
    }
}