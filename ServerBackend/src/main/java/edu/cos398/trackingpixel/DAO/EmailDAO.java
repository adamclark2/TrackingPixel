package edu.cos398.trackingpixel.DAO;

import java.util.List;

import edu.cos398.trackingpixel.Model.EmailCampaign;

public interface EmailDAO {
    public List<String> sendEmail(EmailCampaign camp);
}