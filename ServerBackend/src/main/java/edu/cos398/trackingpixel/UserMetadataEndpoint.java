package edu.cos398.trackingpixel;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cos398.trackingpixel.Model.UserMetadata;
import edu.cos398.trackingpixel.Providers.UserMetadataProvider;

@CrossOrigin
@RestController
public class UserMetadataEndpoint{
    @Autowired
    private UserMetadataProvider ump;

    @RequestMapping(value = "/meta", produces= MediaType.APPLICATION_JSON_VALUE)
    public UserMetadata getMeta(HttpServletRequest req){
        String useragent = req.getHeader("user-agent");
        if(useragent == null || useragent == ""){
            useragent = req.getHeader("User-Agent");
            if(useragent == null || useragent == ""){
                useragent = req.getHeader("UserAgent");
                if(useragent == null || useragent == ""){
                    useragent = req.getHeader("useragent");
                }
            }
        }

        String ipAddress = req.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
          ipAddress = req.getRemoteAddr();  
        }

        return ump.getUserMetadata(useragent, ipAddress);
    }
}