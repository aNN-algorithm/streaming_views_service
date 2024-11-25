package com.example.streaming.batch.partitioner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class RedisIdRangePartitioner implements Partitioner {

    private final RedisTemplate<String, Object> redisTemplateContentPostId;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("#{jobParameters['date']}")
    private String date;

    @Override public Map<String, ExecutionContext> partition(int gridSize)
    {
        //LocalDate localDate = LocalDate.parse(date).minusDays(1);
        LocalDate localDate = LocalDate.parse(date);
        String key = "DailyViews:" + localDate.format(DATE_FORMATTER);
        long dataSize = redisTemplateContentPostId.opsForZSet().size(key);

        long partitionSize = dataSize / gridSize;
        long start = 1L;
        long end = partitionSize;
        Map<String, ExecutionContext> partitions = new HashMap<>();

        for (int i = 1; i <= gridSize; i++) {
            ExecutionContext executionContext = new ExecutionContext();
            partitions.put("partition" + i, executionContext);
            executionContext.put("start", start - 1L);

            if (i == gridSize && end < dataSize) {
                end = dataSize;
            }
            executionContext.put("end", end - 1L);
            start += partitionSize;
            end += partitionSize;
        }

        return partitions;
    }
}
