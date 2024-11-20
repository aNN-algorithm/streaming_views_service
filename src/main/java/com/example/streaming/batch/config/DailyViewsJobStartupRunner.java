//package com.example.streaming.batch.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class DailyViewsJobStartupRunner {
//
//    private final DailyViewsJobLauncherConfig jobLauncherConfig;
//
//    @Bean
//    public ApplicationRunner applicationRunner() {
//        return args -> {
//            try {
//                jobLauncherConfig.runDailyViewsJob();
//            } catch (JobExecutionException e) {
//                log.error(e.getMessage());
//            }
//        };
//    }
//}
