package com.ssafy.kkoma.scheduler;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class ReadDealTableScheduler {

    @Autowired
    private Job readDealTableJob;

    @Autowired
    private JobLauncher jobLauncher;

    private LocalDateTime lastRun = LocalDateTime.now();
    private LocalDateTime curRun;

    @Scheduled(cron = "0 */2 * * * *") // once per 2 minutes (at the top of the minute - the 0th second).
    public void readDealTableJobRun() throws
            JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException
    {
        curRun = LocalDateTime.now();
        log.info("[1/lastRun] {} ", lastRun);
        log.info("[1/curRun] {}", curRun);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("lastRun", lastRun)
                .addLocalDateTime("curRun", curRun)
                .toJobParameters();

        lastRun = curRun;
        log.info("[2/lastRun] {} ", lastRun);
        log.info("[2/curRun] {}", curRun);

        jobLauncher.run(readDealTableJob, jobParameters);
    }
}
