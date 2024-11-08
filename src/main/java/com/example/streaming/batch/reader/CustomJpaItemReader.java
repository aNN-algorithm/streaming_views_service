package com.example.streaming.batch.reader;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import com.example.streaming.common.entity.UserViewLogEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomJpaItemReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RedisTemplate<String, Object> redisTemplateContentPostId;
    private final EntityManagerFactory entityManagerFactory;

    public CustomJpaItemReader(@Qualifier("redisTemplateContentPostId") RedisTemplate<String, Object> redisTemplateContentPostId,
                               EntityManagerFactory entityManagerFactory
    ) {
        this.redisTemplateContentPostId = redisTemplateContentPostId;
        this.entityManagerFactory = entityManagerFactory;
    }

    public JpaPagingItemReader<CumulativeContentStatisticsEntity> createJpaPagingItemReader() {

        log.info("JpaPagingItemReader createJpaPagingItemReader");

        LocalDateTime date = LocalDateTime.now().minusDays(1);
        //String key = "DailyView:" + date.format(DATE_FORMATTER);
        String key = "DailyViews:" + date.format(DATE_FORMATTER);
        log.info("key : {} ", key);

        Set<Object> redisData = redisTemplateContentPostId.opsForSet().members(key);

        if (redisData == null || redisData.isEmpty()) {
            log.info("No data found in Redis for key : {}", key);
            return null;
        }

        // Set<Object>를 Set<Long>으로 변환
        Set<Long> contentIds = redisData.stream()
                .filter(item -> item instanceof Long) // Long 타입인지 확인
                .map(item -> (Long) item) // Object를 Long으로 변환
                .collect(Collectors.toSet());

        if (contentIds.isEmpty()) {
            log.info("No valid Long content IDs found in Redis data");
            return null; // 변환 후 사용할 데이터가 없는 경우 null 반환
        }

        log.info("Found {} items in Redis for key : {}", contentIds.size(), key);

        return new JpaPagingItemReaderBuilder<CumulativeContentStatisticsEntity>()
                .name("userViewLogReader")
                .entityManagerFactory(entityManagerFactory)
                //.queryString("SELECT l FROM UserViewLogEntity l WHERE l.playbackDatetime = BETWEEN :startOfDay AND :endOfDay AND l.contentPostId IN :contentIds")
                //.queryString("SELECT l FROM UserViewLogEntity l WHERE DATE(l.playbackDatetime) = :date AND l.contentPostId IN :contentIds")
                .queryString("SELECT c FROM CumulativeContentStatisticsEntity c WHERE c.contentPostId IN :contentIds")
                .parameterValues(createParameterMap(date, contentIds))
                .pageSize(10)
                .build();
    }

    private Map<String, Object> createParameterMap(LocalDateTime date, Set<Long> contentIds) {
        log.info("createParameterMap");

//        LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
//        Date sqlDate = Date.valueOf(localDate);
//
//        log.info("date : {} ", localDate);
        log.info("contentIds : {} ", contentIds);

        Map<String, Object> parameterMap = new HashMap<>();
//        parameterMap.put("date", sqlDate);
        parameterMap.put("contentIds", contentIds);
        return parameterMap;
    }
}