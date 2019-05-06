package edu.cos398.trackingpixel.Providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.cos398.trackingpixel.DAO.impl.*;
import edu.cos398.trackingpixel.DAO.*;

@Configuration
public class ProviderConfig{
    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.smtp}")
    private String smtp;

    @Value("${email.smtp.auth}")
    private String smtpAuth;

    @Value("${email.smtp.starttls.enable}")
    private String starttls;

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private String smtpPort;

    @Value("${email.use-mock}")
    private String useMock;

    @Value("${server.hostAddress}")
    private String hostAddress;

    @Autowired
    private PixelProvider pixelProvider;


    public ProviderConfig(){
    }

    @Bean
    public EmailDAO getEmail(){
        if(Boolean.parseBoolean(useMock)){
            return new MockEmailDAO(pixelProvider);
        }else{
            return 
            new RealEmailDAO(
                 username,
                 password,
                 smtp,
                 smtpAuth,
                 starttls,
                 smtpHost,
                 smtpPort,

                 pixelProvider,
                 hostAddress
            );
        }
    }
}