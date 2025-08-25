package com.fcm.notification.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcm.notification.config.FcmUtils;
import com.fcm.notification.constants.MessageConstants;
import com.fcm.notification.constants.ServiceConstants;
import com.fcm.notification.payload.NotificationJob;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * The type Job initializer.
 */
@Component
public class JobInitializer implements ApplicationRunner {

    private final FcmUtils fcmUtils;
    private final NotificationScheduler scheduler;

    /**
     * Instantiates a new Job initializer.
     *
     * @param fcmUtils  the fcm utils
     * @param scheduler the scheduler
     */
    public JobInitializer(FcmUtils fcmUtils, NotificationScheduler scheduler) {
        this.fcmUtils = fcmUtils;
        this.scheduler = scheduler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream in = getClass().getResourceAsStream(ServiceConstants.FIREBASE_NOTIFICATION_JOB_CONTENT);
        List<NotificationJob> jobs = objectMapper.readValue(in, new TypeReference<List<NotificationJob>>() {
        });
        List<String> tokens = fcmUtils.fcmTokens;

        int i = 1;
        for (NotificationJob job : jobs) {
            scheduler.scheduleJob(ServiceConstants.FCM_JOB + i, job, tokens);
            i++;
        }
        System.out.println(MessageConstants.FCM_JOB_SCHEDULE_SUCCESS);
    }
}
