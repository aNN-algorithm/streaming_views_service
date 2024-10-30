package com.example.streaming.watchContent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchCacheServiceImpl implements WatchCacheService {

    private static final long TTL_DURATION = 3600; // 예시: 1시간 (3600초)

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long getLastPlayedAt(Long userId, Long contentPostId) {

        String key = "userId:" + userId;
        log.info("key = {}", key);

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
}
