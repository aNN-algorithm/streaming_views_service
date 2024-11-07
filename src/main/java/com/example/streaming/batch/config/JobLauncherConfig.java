package com.example.streaming.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobLauncherConfig {

    private final JobLauncher jobLauncher;
    private final Job dailyViewsJob;

    public void runDailyViewsJob() throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters();

            jobLauncher.run(dailyViewsJob, jobParameters);
        } catch (JobExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
