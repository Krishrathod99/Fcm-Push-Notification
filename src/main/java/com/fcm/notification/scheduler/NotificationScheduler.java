package com.fcm.notification.scheduler;

import com.fcm.notification.constants.MessageConstants;
import com.fcm.notification.constants.ServiceConstants;
import com.fcm.notification.payload.NotificationJob;
import com.google.firebase.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;


/**
 * The type Notification scheduler.
 */
@Service
public class NotificationScheduler {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> jobsMap = new ConcurrentHashMap<>();

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    /**
     * Instantiates a new Notification scheduler.
     *
     * @param taskScheduler the task scheduler
     */
    public NotificationScheduler(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    /**
     * Schedule job.
     *
     * @param jobId     the job id
     * @param job       the job
     * @param fcmTokens the fcm tokens
     */
    public void scheduleJob(String jobId, NotificationJob job, List<String> fcmTokens) {
        String cron = buildCron(job.getHour(), job.getMinute());

        Runnable task = () -> {
            logger.info(MessageConstants.SENDING_FCM_NOTIFICATION_MESSAGE + job.getTitle() + " - " + job.getSubtitle());
            sendNotificationToAll(fcmTokens, job);
        };

        ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(cron));
        jobsMap.put(jobId, future);
    }

    private String buildCron(Integer hour, Integer minute) {
        return String.format(ServiceConstants.FCM_CRON, minute, hour);
    }

    private void sendNotificationToAll(List<String> tokens, NotificationJob job) {
        MulticastMessage message = getPreconfiguredMessageToToken(tokens, job);
        sendAndGetResponse(message);
    }

    private void sendAndGetResponse(MulticastMessage message) {
        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            System.out.println(MessageConstants.FCM_NOTIFICATION_SENT_SUCCESS + response.getSuccessCount());
        } catch (Exception e) {
            logger.error(e.getCause().getMessage());
            e.printStackTrace();
        }
    }

    private MulticastMessage getPreconfiguredMessageToToken(List<String> tokens, NotificationJob job) {
        MulticastMessage.Builder messageBuilder = getPreconfiguredMessageBuilder(job);
        return messageBuilder.addAllTokens(tokens).build();
    }

    private MulticastMessage.Builder getPreconfiguredMessageBuilder(NotificationJob job) {
        AndroidConfig androidConfig = getAndroidConfig(job.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(job.getTopic());
        Notification notification = Notification.builder()
                .setTitle(job.getTitle())
                .setBody(job.getSubtitle())
                .build();
        return MulticastMessage.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build())
                .build();
    }

}
