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

        # When set to true this the server will pretend to send emails
        # but won't send them. This is good for testing.
        email.use-mock=true

        # Email Settings (For real Email)
        # For gmail you may need to enable IMap, POP, and `Less Secure Apps`
        email.username=**USERNAME**@gmail.com
        email.password=**YOUR PASSWORD HERE**
        email.smtp=smtp.google.com
        email.smtp.auth=true
        email.smtp.starttls.enable=true
        email.smtp.host=smtp.gmail.com
        email.smtp.port=587

        # If db.use-mock is set to true we will use an
        # in memory database. Otherwise we will use mysql
        # If you use the mock, then any password will work to
        # login
        db.use-mock= true
        db.schema=**SCHEMA**
        db.mysql.url=jdbc:mysql://{mysql url here}
        db.mysql.username=**USERNAME**
        db.mysql.password=**YOUR PASSWORD HERE**

# Deployment (local)

1. Download jdk 
1. Download maven
1. Edit the file `ServerBackend/src/main/resources/application.properties`
    - Add the example above to simply start the app
    - You may need to create the file
1. Edit the file `Dashboard/script.js`
1. CD into ServerBackend on the command line
1. Run the command `mvn clean`
1. Run the command `mvn package`
1. Run the command `java -jar ./target/trackingpixel-0.0.1-SNAPSHOT.jar`
1. Open your browser and go to developer options.
    - Choose `Disable CORS`
    - This needs to be done for a local run because `file:\\\Users\adam\...` is a different domain then `http://localhost:8080/`
1. You can fill in any username and password if you aren't using a database as long as it's not the empty string

# Database & Email
Modify the `application.properties` to connect to a database and-or email.  