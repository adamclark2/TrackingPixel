package edu.cos398.trackingpixel.Providers;

import java.net.InetAddress;
import java.util.Scanner;

import org.apache.commons.net.whois.WhoisClient;
import org.springframework.stereotype.Component;

import edu.cos398.trackingpixel.Model.UserMetadata;

@Component
public class UserMetadataProvider {
    private String[] whoisServers = {
        "whois.arin.net",
        "whois.iana.org",
        "whois.ripe.net",
        "Whois.Registrar.Amazon.com"
    };

private String[] knownISP = {"time warner", "comcast", "sprint", "att", "at&t", "verizon", "gwi", "fairpoint", "fair-point", "consolidated communications", "amazon" /* amazon aws */, "tmobile", "t-mobile"};

    public UserMetadata getUserMetadata(String useragent, String ipAddress) {
        String OS = null;
        String browser = null;
        String location = null;
        String isp = null;
        String usableHostName = null;

        useragent = useragent == null ? "" : useragent;

        String osAgentSubstring = getOSSubstring(useragent);
        OS = getOSFromOSSubsString(osAgentSubstring);
        browser = getBrowser(osAgentSubstring, useragent);

        if (browser == null) {
            // Special browsers (command line)
            if (useragent.contains("lynx")) {
                browser = "lynx";
                if (OS == null) {
                    OS = "Unix";
                }
            } else if (useragent.toLowerCase().contains("wget") || useragent.toLowerCase().contains("w-get")) {
                browser = "wget";
                if (OS == null) {
                    OS = "Unix";
                }
            } else if (useragent.toLowerCase().contains("curl")) {
                browser = "curl";
                if (OS == null) {
                    OS = "Unix";
                }
            }
        }

        if (ipAddress.toLowerCase().equals("localhost") || ipAddress.toLowerCase().equals("127.0.0.1")) {
            location = "Localhost";
        }

        if (location == null) {
            try {
                String canon = InetAddress.getByName(ipAddress).getCanonicalHostName();
                String noncanon = InetAddress.getByName(ipAddress).getHostName();

                if (canon.length() > noncanon.length()) {
                    usableHostName = canon;
                } else {
                    usableHostName = noncanon;
                }

                if (usableHostName.toLowerCase().contains("cs.usm.maine.edu")) {
                    location = "USM Computer Scicent Lab";
                    isp = "University of Maine System (USM is its own isp)";
                } else if (usableHostName.toLowerCase().contains("usm.maine.edu")) {
                    location = "USM";
                    isp = "University of Maine System (USM is its own isp)";
                }
            } catch (Exception e) {
                // oh well
            }
        }

        String[] vals = {"","","No whois"};
        if(isp == null && location == null && !usableHostName.toLowerCase().trim().equals("localhost")){
            vals = getWhois(ipAddress, location, isp);
            isp = vals[1];
            location = vals[0];
        }

        if(usableHostName.trim().toLowerCase().equals("localhost")){
            isp = "Local IP's don't need an ISP (or we can't find it)";
        }

        if(isp != null && isp.toLowerCase().trim().contains("These addresses were assigned by the IETF, the organization that develops Internet protocols, in the Best Current Practice document".toLowerCase().trim())){
            isp = "Local IP's don't need an ISP (or we can't find it)";
        }
        UserMetadata meta = new UserMetadata(OS, browser, location, isp, usableHostName);
        meta.debugInfo = vals[2];
        return meta;
    }

    private String getBrowser(String osAgentSubstring, String useragent) {
        String browser = null;
        if (osAgentSubstring.toLowerCase().contains("msie") || osAgentSubstring.toLowerCase().contains("trident")) {
            browser = "Internet Explorer";
        }

        // These browsers are formed from others and may contain
        // 'firefox', 'chrome', and-or 'safari'
        else if (useragent.toLowerCase().contains("edge")) {
            browser = "Edge";
        } else if (useragent.toLowerCase().contains("camino")) {
            browser = "Camino";
        } else if (useragent.toLowerCase().contains("opera")) {
            browser = "Opera";
        }

        // These browsers form the base of others
        else if (useragent.toLowerCase().contains("firefox")) {
            browser = "Firefox";
        } else if (useragent.toLowerCase().contains("chrome")) {
            browser = "Chrome";
        } else if (useragent.toLowerCase().contains("safari")) {
            browser = "Safari";
        }

        return browser;
    }

    private String getOSFromOSSubsString(String osAgentSubstring) {
        String OS = null;
        if (osAgentSubstring.toLowerCase().contains("macintosh") || osAgentSubstring.toLowerCase().contains("ipad")
                || osAgentSubstring.toLowerCase().contains("ipod")
                || osAgentSubstring.toLowerCase().contains("iphone")) {
            Scanner sc = new Scanner(osAgentSubstring);
            sc.useDelimiter(";");
            if (sc.hasNext()) {
                sc.next();
            }
            if (sc.hasNext()) {
                OS = sc.next();
                OS = OS.substring(0, OS.length() - 1); // Remove ending ')'
            } else {
                OS = "Apple";
            }
        } else if (osAgentSubstring.toLowerCase().contains("windows nt")) {
            OS = "Windows";
        } else if (osAgentSubstring.toLowerCase().contains("linux")) {
            OS = "Linux";
        }

        return OS;
    }

    private String getOSSubstring(String useragent) {
        // Get OS substring
        StringBuilder bldr = new StringBuilder();
        boolean start = false;
        char[] useragentarr = useragent.toCharArray();
        for (int i = 0; i < useragentarr.length; i++) {
            if (start) {
                bldr.append(useragentarr[i]);
                if (useragentarr[i] == ')') {
                    i = useragentarr.length + 1;
                }
            } else {
                if (useragentarr[i] == '(') {
                    start = true;
                    bldr.append(useragentarr[i]);
                }
            }
        }

        return bldr.toString();
    }


    private String getValueFromWhois(String input){
        StringBuilder bldr = new StringBuilder();

        boolean append = false;
        for(char c : input.toCharArray()){
            if(append){
                bldr.append(c);
            }

            if(c == ':' || c == '='){
                append = true;
            }
        }
        return bldr.toString();
    }

    private String[] getWhois(String name, String location, String isp) {
        String whoIs[] = new String[whoisServers.length];
        int idx = 0;
        for(String whoisServer : whoisServers){
            if(isp == null || location == null){
                WhoisClient whois = new WhoisClient();
                try {
                    whois.connect(whoisServer);
                    String whoisData1 = whois.query(name);
                    System.err.println("\n\n\n\n\n\n");
                    System.err.println(whoisData1);
                    whoIs[idx++] = whoisData1;
                    whois.disconnect();

                    if(!whoisData1.toLowerCase().contains("changed:")){ // main whois servers can change from whois' 
                        Scanner sc = new Scanner(whoisData1);
                        while(sc.hasNextLine()){
                            String val = sc.nextLine();
                            if(val.toLowerCase().contains("Legacy Time Warner Cable IP Assets".toLowerCase())){
                                isp = "Time Warner Legacy";
                            }

                            if(val.toLowerCase().contains("state") && location == null){
                                String newLoc = getValueFromWhois(val);
                                if(!newLoc.toLowerCase().trim().equals("")){
                                    location = newLoc;
                                    location = location.trim();
                                }
                            }

                            if(val.toLowerCase().contains("org") || val.toLowerCase().contains("Organization:".toLowerCase())){
                                String newIsp = getValueFromWhois(val);
                                if(isp == null && !newIsp.toLowerCase().contains("iana") && !newIsp.trim().equals("")) {
                                    isp = newIsp;
                                    isp = isp.trim();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        String[] ret = {location, isp, ""};
        return ret;
    }
}