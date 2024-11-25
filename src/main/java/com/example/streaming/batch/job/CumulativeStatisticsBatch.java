package com.example.streaming.batch.job;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CumulativeStatisticsBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ItemReader<DailyContentStatistics> itemReader;
    private final ItemProcessor<DailyContentStatistics, CumulativeContentStatistics> itemProcessor;
    private final ItemWriter<CumulativeContentStatistics> itemWriter;

    @Bean
    public Job cumulativeStatisticsJob() {

        return new JobBuilder("CumulativeStatisticsJob", jobRepository)
                .start(cumulativeStatisticsStep())
                .build();
    }

    @Bean
    public Step cumulativeStatisticsStep() {

        return new StepBuilder("CumulativeStatisticsStep", jobRepository)
                .<DailyContentStatistics, CumulativeContentStatistics>chunk(3, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }
}
