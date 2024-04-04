package com.ssafy.kkoma.scheduler;

import com.ssafy.kkoma.domain.notification.entity.Notification;
import com.ssafy.kkoma.domain.notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class DealReminderScheduler {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private NotificationRepository notificationRepository;

    public void scheduling(Notification noti, Instant remindTime) {
        taskScheduler.schedule(dealReminderJobRun(noti), remindTime);
    }

    public Runnable dealReminderJobRun(Notification noti) {
        return () -> {
            notificationRepository.save(noti);
        };
    }
}
