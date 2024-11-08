package com.example.streaming.batch.job;

import com.example.streaming.batch.reader.CustomJpaItemReader;
import com.example.streaming.batch.writer.CustomItemWriter;
import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import com.example.streaming.common.entity.UserViewLogEntity;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class StatisticBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final CustomJpaItemReader customJpaItemReader;
    private final ItemProcessor<CumulativeContentStatisticsEntity, DailyContentStatistics> itemProcessor;
    private final ItemWriter<DailyContentStatistics> itemWriter;

    public StatisticBatch(JobRepository jobRepository,
                          PlatformTransactionManager platformTransactionManager,
                          CustomJpaItemReader customJpaItemReader,
                          ItemProcessor<CumulativeContentStatisticsEntity, DailyContentStatistics> itemProcessor,
                          ItemWriter<DailyContentStatistics> itemWriter
    ) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.customJpaItemReader = customJpaItemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Bean
    public Job dailyViewsJob() {

        log.info("create job : DailyViewsJob");

        return new JobBuilder("DailyViewsJob", jobRepository)
                .start(dailyViewsStep())
                .build();
    }

    @Bean
    public Step dailyViewsStep() {
        log.info("create step : DailyViewsStep");

        //JpaPagingItemReader<UserViewLogEntity> reader = customJpaItemReader.createJpaPagingItemReader();
        JpaPagingItemReader<CumulativeContentStatisticsEntity> reader = customJpaItemReader.createJpaPagingItemReader();

        return new StepBuilder("DailyViewsStep", jobRepository)
                //.<UserViewLogEntity, DailyContentStatisticsEntity>chunk(3, platformTransactionManager)
                .<CumulativeContentStatisticsEntity, DailyContentStatistics>chunk(3, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .listener(new ItemReadListener<UserViewLogEntity>() {
                    @Override
                    public void beforeRead() {
                        log.info("start to read an item...");
                    }

                    @Override
                    public void afterRead(UserViewLogEntity item) {
                        log.info("finish to read an item");
                    }

                    @Override
                    public void onReadError(Exception e) {
                        log.error("read error", e);
                    }
                })
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("Before step: {}", stepExecution.getStepName());
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        log.info("Step completed: {}, readCount: {}, writeCount: {}",
                                stepExecution.getStepName(),
                                stepExecution.getReadCount(),
                                stepExecution.getWriteCount());
                        return stepExecution.getExitStatus();
                    }
                })
                .build();
    }

//    @Bean
//    public ItemReader<Long> itemReader() {
//        log.info("create itemReader and new CustomItemReader1");
//        return new CustomItemReader1();
//    }

//    @Bean
//    //public ItemProcessor<UserViewLogEntity, DailyContentStatisticsEntity> itemProcessor() {
//    public ItemProcessor<CumulativeContentStatisticsEntity, DailyContentStatisticsEntity> itemProcessor() {
//        log.info("create itemProcessor");
//        return new CustomItemProcessor();
//    }

//    @Bean
//    public ItemWriter<DailyContentStatistics> itemWriter() {
//        log.info("create itemWriter");
//        return new CustomItemWriter();
//    }
}
