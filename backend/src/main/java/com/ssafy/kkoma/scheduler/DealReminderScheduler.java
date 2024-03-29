package com.ssafy.kkoma.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DealReminderScheduler {

    @Autowired
    private Job dealReminderJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0 * * * * MON-SUN") // once per minute (at the top of the minute - the 0th second).
    public void dealReminderJobRun() throws
        JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
        JobParametersInvalidException, JobRestartException
    {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("requestTime", LocalDateTime.now()).toJobParameters();

        jobLauncher.run(dealReminderJob, jobParameters);
    }
}
