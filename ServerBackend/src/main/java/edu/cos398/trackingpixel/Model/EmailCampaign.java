package edu.cos398.trackingpixel.Model;

public class EmailCampaign {
    private String[] to;
    private String content;
    private String subject;

    public EmailCampaign(){}

    public String[] getReceivers(){
        return to;
    }

    public String getContent(){
        return content;
    }

    public String getSubject(){
        return subject;
    }
}