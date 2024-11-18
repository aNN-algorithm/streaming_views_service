package com.example.streaming.watchContent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WatchCacheToDBService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int BATCH_SIZE = 1000;

    private final JdbcTemplate jdbcTemplate;

    public WatchCacheToDBService(RedissonClient redissonClient,
                                 JdbcTemplate jdbcTemplate,
                                 @Qualifier("redisTemplateToCalculateViews") RedisTemplate<String, Object> redisTemplate) {
        this.redissonClient = redissonClient;
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    @Scheduled(cron = "5 * * * * ?")
    public void transferCacheToDBEveryMinute() {
        log.info("Hello, schedule to transfer cache to DB");

        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        String key = "view:" + DATE_TIME_FORMATTER.format(oneMinuteAgo);
        log.info("key = {}", key);
        Map<Object, Object> entireHash = redisTemplate.opsForHash().entries(key);

        if (entireHash == null || entireHash.isEmpty()) {
            return;
        }

        Boolean keyExists = redisTemplate.hasKey(key);
        log.info("Does the key exist in Redis? {}", keyExists);

        // Redis 에서 락을 가져와서 동시 접근을 막음
        RLock lock = redissonClient.getLock(key);
        try {
            log.info("lock = {}", lock);
            // 락 획득 시도 (최대 5초 대기) 및 30초 동안 락 유지
            if (lock.tryLock(5, 30, TimeUnit.SECONDS)) {
                log.info("Get lock!!");
                Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().build());

                while (cursor.hasNext()) {
                    List<Map.Entry<Object, Object>> batch = new ArrayList<>();
                    for (int i = 0; i < BATCH_SIZE && cursor.hasNext(); i++) {
                        batch.add(cursor.next());
                    }
                    saveToDB(batch);
                    deleteDataInCache(batch, oneMinuteAgo);
                }
            }
        } catch (InterruptedException e) {
            log.info("Exception occurred while trying to get lock");
            log.error(e.getMessage(), e);
        } finally {
            log.info("finally try-catch");
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        log.info("end to transfer cache to DB");
    }

    public void saveToDB(List<Map.Entry<Object, Object>> batch) {

        log.info("start to save to db");
        String sql = "UPDATE content_post SET total_views = total_views + ? WHERE id = ?";

        List<Object[]> batchArgs = new ArrayList<>();
        for (Map.Entry<Object, Object> update : batch) {
            batchArgs.add(new Object[]{update.getValue(), update.getKey()});
            log.info("postId = {}, viewCount = {}", update.getKey(), update.getValue());
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public void deleteDataInCache(List<Map.Entry<Object, Object>> batch, LocalDateTime oneMinuteAgo) {

        log.info("start to delete data in cache");
        batch.forEach(entry -> {
            try {
                String redisKey = "view:" + DATE_TIME_FORMATTER.format(oneMinuteAgo);
                String hashKey = (String) entry.getKey();
                redisTemplate.opsForHash().delete(redisKey, hashKey);
                log.info("Delete key from Redis = {}", redisKey);
            } catch (Exception e) {
                log.error("Failed to delete key from Redis = {}", entry.getKey(), e);
            }
        });
    }
}
