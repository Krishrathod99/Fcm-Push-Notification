package com.fcm.notification.constants;


/**
 * The type Service constants.
 */
public class ServiceConstants {

    /**
     * The constant FCM_CRON.
     */
    public static final String FCM_CRON = "0 %d %d * * *";

    /**
     * The constant FCM_JOB.
     */
    public static final String FCM_JOB = "job ";

    /**
     * The constant FIREBASE_DB_COLLECTION.
     */
    public static final String FIREBASE_DB_COLLECTION = "Users";

    /**
     * The constant FCM_TOKEN.
     */
    public static final String FCM_TOKEN = "fcmToken";

    /**
     * The constant SCHEDULER_THREAD_NAME.
     */
    public static final String SCHEDULER_THREAD_NAME = "notification-scheduler";

    /**
     * The constant FIREBASE_NOTIFICATION_JOB_CONTENT.
     */
    public static final String FIREBASE_NOTIFICATION_JOB_CONTENT = "/schedule.json";
}
