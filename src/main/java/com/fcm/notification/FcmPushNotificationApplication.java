package com.fcm.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * The type Fcm push notification application.
 */
@SpringBootApplication
@EnableScheduling
public class FcmPushNotificationApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FcmPushNotificationApplication.class, args);
    }

}
