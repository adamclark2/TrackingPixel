package edu.cos398.trackingpixel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping(path = "/", produces = "text/plain")
    public String index(){
        return "Talk to GUI frontend or REST api!\n...";
    }

}
