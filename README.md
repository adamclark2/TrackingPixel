# TrackingPixel
An implementation of Tracking Pixels to explore ethical implications (For a College class)

# application.properties
this is a config file used by us and spring framework to get information. 
Here are all the fields you need

                # Send JSON that I can read
                spring.gson.pretty-printing=true

                # Server Settings (we use)
                # no trailing slash 'http://localhost:8080'
                server.hostAddress=http://localhost:8080

                # Email Settings
                # For gmail you may need to enable IMap, POP, and `Less Secure Apps`
                email.username=example@gmail.com
                email.password=**YOUR PASSWORD GOES HERE**
                email.smtp=smtp.google.com
                email.smtp.auth=true
                email.smtp.starttls.enable=true
                email.smtp.host=smtp.gmail.com
                email.smtp.port=587

# Deployment
... Talk about how to deploy here ...