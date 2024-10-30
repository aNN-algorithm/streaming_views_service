package com.example.streaming.service;

import com.example.streaming.watchContent.service.WatchCacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WatchCacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private WatchCacheServiceImpl watchCacheService;

    @BeforeEach
    public void setup() {
        // Mockito 어노테이션 초기화
        MockitoAnnotations.openMocks(this);
        // redisTemplate 의 opsForHash() 호출이 hashOperations 를 반환하도록 설정
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void testGetLastPlayedAt() {
        // given
        Long userId = 1L;
        Long contentPostId = 101L;
        Long expectedLastPlayedAt = 123456789L;

        String key = "userId:" + userId;

        // when - hashOperations.get() 호출 시 expectedLastPlayedAt을 반환하도록 설정
        when(hashOperations.get(key, String.valueOf(contentPostId))).thenReturn(expectedLastPlayedAt);

        // then - 서비스 메서드 호출 및 검증
        Long lastPlayedAt = watchCacheService.getLastPlayedAt(userId, contentPostId);
        assertEquals(expectedLastPlayedAt, lastPlayedAt);
    }

    @Test
    public void testUpdateLastPlayedAt() {
        // given
        Long userId = 1L;
        Long contentPostId = 101L;
        Long lastPlayedAt = 123456789L;

        String key = "userId:" + userId;

        // when - 서비스 메서드 호출
        watchCacheService.updateLastPlayedAt(userId, contentPostId, lastPlayedAt);

        // then - hashOperations.put()과 redisTemplate.expire() 호출 검증
        // mock 객체 검증
        verify(hashOperations, times(1)).put(key, String.valueOf(contentPostId), String.valueOf(lastPlayedAt));
        verify(redisTemplate, times(1)).expire(key, 3600, TimeUnit.SECONDS);
    }
}
