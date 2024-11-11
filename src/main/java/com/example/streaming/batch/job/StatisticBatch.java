package com.example.streaming.batch.job;

import com.example.streaming.batch.partitioner.RedisIdRangePartitioner;
import com.example.streaming.common.entity.UserViewLogEntity;
import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StatisticBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ItemReader<CumulativeContentStatistics> itemReader;
    private final ItemProcessor<CumulativeContentStatistics, DailyContentStatistics> itemProcessor;
    private final ItemWriter<DailyContentStatistics> itemWriter;
    private final RedisIdRangePartitioner redisIdRangePartitioner;

    private static final String JOB_NAME = "DailyViewsJob";
    private static final int POOL_SIZE = 5;

    @Bean
    public Job dailyViewsJob() {

        //TODO: 로그는 리스너로 찍을 것
        log.info("create job : DailyViewsJob");

        return new JobBuilder("DailyViewsJob", jobRepository)
                .start(dailyViewsPartitionStep()) // PartitionStep 생성
                .build();
    }

    @Bean
    public Step dailyViewsPartitionStep() {
        log.info("Creating partition step: DailyViewsPartitionStep");

        return new StepBuilder("DailyViewsPartitionStep", jobRepository)
                .partitioner("DailyViewsStep", redisIdRangePartitioner) // 파티셔너 설정
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public TaskExecutorPartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setStep(dailyViewsStep());
        handler.setTaskExecutor(taskExecutor());
        handler.setGridSize(POOL_SIZE);
        return handler;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(POOL_SIZE);
        taskExecutor.setMaxPoolSize(POOL_SIZE);
        taskExecutor.setThreadNamePrefix("partition-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public Step dailyViewsStep() {
        log.info("create step : DailyViewsStep");

        return new StepBuilder("DailyViewsStep", jobRepository)
                .<CumulativeContentStatistics, DailyContentStatistics>chunk(3, platformTransactionManager)
                .reader(itemReader)
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
}
