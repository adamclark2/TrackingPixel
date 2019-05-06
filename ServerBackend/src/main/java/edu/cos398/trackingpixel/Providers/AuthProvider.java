package edu.cos398.trackingpixel.Providers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.cos398.trackingpixel.DAO.AuthDAO;

@Component
public class AuthProvider {

    @Autowired
    private AuthDAO authDAO;

    public boolean login(HttpSession session, String userName, String password){
        if(authDAO.auth(userName, password)){
            session.setAttribute("isLoggedIn", true);
            return true;
        }else{
            return false;
        } 
    }

    public boolean isLoggedIn(HttpSession session){
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        return isLoggedIn != null && isLoggedIn;
    }

    public boolean logout(HttpSession session){
        session.setAttribute("isLoggedIn", null);
        return true;
    }
}