package com.fcm.notification.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The type Notification job.
 */
@NoArgsConstructor
@AllArgsConstructor
public class NotificationJob {

    private Integer minute;

    private Integer hour;

    private String topic;

    private String title;

    private String subtitle;

    /**
     * Gets minute.
     *
     * @return the minute
     */
    public Integer getMinute() {
        return minute;
    }

    /**
     * Sets minute.
     *
     * @param minute the minute
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * Sets hour.
     *
     * @param hour the hour
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets subtitle.
     *
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets subtitle.
     *
     * @param subtitle the subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
