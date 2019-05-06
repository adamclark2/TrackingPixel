package edu.cos398.trackingpixel.Model;

public class UserMetadata{
    private String OS = "Unknown OS";
    private String browser = "Unknown Browser";
    private String location = "Unknown Location";
    private String isp = "Unknown ISP";
    private String hostname = "Unknown Hostname";
    public String debugInfo = "";

    public UserMetadata(String OS, String browser, String location, String isp, String hostname){
        this.OS = OS == null ? this.OS : OS;
        this.location= location == null ? this.location : location;
        this.browser = browser == null ? this.browser : browser;
        this.isp = isp == null ? this.isp : isp;
        this.hostname = hostname == null ? this.hostname : hostname;
    }
}