package com.fcm.notification.config;

import com.fcm.notification.constants.ServiceConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * The type Scheduler config.
 */
@Configuration
public class SchedulerConfig {

    /**
     * Task scheduler thread pool task scheduler.
     *
     * @return the thread pool task scheduler
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix(ServiceConstants.SCHEDULER_THREAD_NAME);
        scheduler.initialize();
        return scheduler;
    }
}
