package com.example.streaming.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CumulativeViewsJobLauncherConfig {

    private final JobLauncher jobLauncher;
    private final Job cumulativeStatisticsJob;

    public void runDailyViewsJob(String date) throws JobExecutionException {
        try {
            JobParameters dailyViewsJobParameters = new JobParametersBuilder()
                    .addString("date", date)
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(cumulativeStatisticsJob, dailyViewsJobParameters);
        } catch (JobExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
