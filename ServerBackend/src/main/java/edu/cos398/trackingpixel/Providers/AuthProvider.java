package edu.cos398.trackingpixel.Providers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class AuthProvider {
    public boolean login(HttpSession session, String userName, String password){
        session.setAttribute("isLoggedIn", true);
        return true;
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