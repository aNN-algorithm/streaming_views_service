package com.example.streaming.batch.controller;

import com.example.streaming.batch.config.CumulativeViewsJobLauncherConfig;
import com.example.streaming.batch.config.DailyViewsJobLauncherConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final DailyViewsJobLauncherConfig dailyViewsJobLauncherConfig;
    private final CumulativeViewsJobLauncherConfig cumulativeViewsJobLauncherConfig;

    @GetMapping("/daily-views-batch")
    public String runDailyViewsBatch(@RequestParam String date) {
        try {
            dailyViewsJobLauncherConfig.runDailyViewsJob(date);
            return "Batch daily job for date " + date + " has completed";
        } catch (JobExecutionException e) {
            log.error(e.getMessage());
            return "Batch job for date " + date + " has failed";
        }
    }

    @GetMapping("/cumulative-views-batch")
    public String runCumulativeViewsBatch(@RequestParam String date) {
        try {
            cumulativeViewsJobLauncherConfig.runDailyViewsJob(date);
            return "Batch cumulative job for date " + date + " has completed";
        } catch (JobExecutionException e) {
            log.error(e.getMessage());
            return "Batch job for date " + date + " has failed";
        }
    }
}
