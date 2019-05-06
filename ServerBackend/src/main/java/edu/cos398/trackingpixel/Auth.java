package edu.cos398.trackingpixel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.Providers.AuthProvider;

@RestController
@CrossOrigin()
public class Auth {

    @Autowired
    private AuthProvider ap;

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public void isLoggedIn(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession(true);
        Boolean isLoggedIn = ap.isLoggedIn(session);
        if(isLoggedIn != null && isLoggedIn){
            // 200
            resp.setStatus(HttpServletResponse.SC_OK);
        }else{
            // 401
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, HttpServletResponse resp, @RequestHeader String username, @RequestHeader String password){
        HttpSession session = req.getSession(true);
        boolean canLogin = username != null && password != null && !username.trim().equals("") && !password.trim().equals("");
        canLogin = canLogin & ap.login(session, username, password);
        if(canLogin){
            session.setAttribute("isLoggedIn", true);
            // 200
            resp.setStatus(HttpServletResponse.SC_OK);
        }else{
            // 401
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession(true);
        ap.logout(session);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}   