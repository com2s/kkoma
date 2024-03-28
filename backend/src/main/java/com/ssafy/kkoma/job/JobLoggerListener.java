package com.ssafy.kkoma.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    private static String BEFORE_MESSAGE = "{} Job을 실행합니다.";
    private static String AFTER_MESSAGE = "{} Job이 종료되었습니다. (Status: {})";
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE,
            jobExecution.getJobInstance().getJobName(),
            jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // email이나 메신저 발송
            log.info("Job failed");
        }
    }
}
