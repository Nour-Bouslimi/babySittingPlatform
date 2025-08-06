package org.example.babysitting;

import org.example.babysitting.serviceImplement.EmailSenderService;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BabySittingApplication {
    static {
        System.load(System.getProperty("user.dir") + "/native/opencv_java4110.dll");
    }
    public static void main(String[] args) {

        SpringApplication.run(BabySittingApplication.class, args);

       // System.out.println("OpenCV version: " + Core.VERSION);
    }


   // @EventListener(ApplicationReadyEvent.class)
    /*public void sendEmail() {
        emailSenderService.sendEmail("bouslimi.nourelhouda@gmail.com", "Welcome to BabySitting Application", "Hello, thank you for registering with us! We are excited to have you on board. If you have any questions or need assistance, feel free to reach out. Enjoy your experience with Allo Nounou!");
    }*/
}