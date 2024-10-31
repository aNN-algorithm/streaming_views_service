package com.example.streaming.watchContent.service;

import com.example.streaming.watchContent.model.UserViewLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchCacheServiceImpl implements WatchCacheService {

    private static final long TTL_DURATION = 3600; // 예시: 1시간 (3600초)
    private static final long TTL_ABUSING = 30;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long getLastPlayedAt(Long userId, Long contentPostId) {

        String key = "userId:" + userId;
        log.info("To get lastPlayedAt key = {}", key);

        Long lastPlayedAt = (Long) redisTemplate.opsForHash().get(key, String.valueOf(contentPostId));
        log.info("getLastPlayedAt lastPlayedAt = {}", lastPlayedAt);

        return lastPlayedAt;
    }

    @Override
    public void updateLastPlayedAt(Long userId, Long contentPostId, Long lastPlayedAt) {

        String key = "userId:" + userId;
        log.info("key = {}, lastPlayedAt = {}", key, lastPlayedAt);

        redisTemplate.opsForHash().put(key, String.valueOf(contentPostId), String.valueOf(lastPlayedAt));

        // TTL 설정 (key 에 TTL 적용)
        redisTemplate.expire(key, TTL_DURATION, TimeUnit.SECONDS);
    }

    @Override
    public void updatePostView(UserViewLog userViewLog) {

        if (isAbusing(userViewLog.getUserId(), userViewLog.getContentPostId())) {
            log.info("isAbusing!! Not increase view of contentPostId = {}", userViewLog.getContentPostId());
            return;
        }

        incrementPostView(userViewLog.getUserId(), userViewLog.getContentPostId(), userViewLog.getPlaybackDateTime());
    }

    @Override
    public void incrementPostView(Long userId, Long contentPostId, LocalDateTime viewDateTime) {

        String key = "view:" + viewDateTime.format(DATE_TIME_FORMATTER);
        log.info("Increment postView key = {}", key);

        redisTemplate.opsForHash().increment(key, String.valueOf(contentPostId), 1L);

        log.info("View count for contentPostId {} has been incremented.", contentPostId);
    }

    @Override
    public boolean isAbusing(Long userId, Long contentPostId) {
        String key = "isAbusing:" + userId + ":" + contentPostId;
        Long value = (Long) redisTemplate.opsForValue().get(key);
        log.info("isAbusing value = {}", value);
        return value != null;
    }

    @Override
    public void cacheAbuseDetectionData(Long userId, Long contentPostId) {
        String key = "isAbusing:" + userId + ":" + contentPostId;
        log.info("start cache = {}", key);
        redisTemplate.opsForValue().set(key, String.valueOf(contentPostId), TTL_ABUSING, TimeUnit.SECONDS);
    }
}
