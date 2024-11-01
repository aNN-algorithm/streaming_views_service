package com.example.streaming.service;

import com.example.streaming.watchContent.model.UserViewLog;
import com.example.streaming.watchContent.service.WatchCacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WatchCacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private WatchCacheServiceImpl watchCacheService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    public void setup() {
        // Mockito 어노테이션 초기화
        MockitoAnnotations.openMocks(this);
        // redisTemplate 의 opsForHash() 호출이 hashOperations 를 반환하도록 설정
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        // redisTemplate 의 opsForValue() 호출이 valueOperations 를 반환하도록 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void 캐시에서_마지막_시청_지점을_가져오기() {
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
    public void 캐시에_마지막_시청_지점을_저장하기() {
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

    @Test
    public void 어뷰징_캐시에_저장하기() {
        // given
        Long userId = 1L;
        Long contentPostId = 101L;
        String key = "isAbusing:" + userId + ":" + contentPostId;

        // when - 서비스 메서드 호출
        watchCacheService.cacheAbuseDetectionData(userId, contentPostId);

        // then - valueOperations.set() 호출 검증
        verify(valueOperations, times(1)).set(key, String.valueOf(contentPostId), 30, TimeUnit.SECONDS);
    }

    @Test
    public void 어뷰징_여부_확인하기() {
        // given
        Long userId = 1L;
        Long contentPostId = 101L;
        String key = "isAbusing:" + userId + ":" + contentPostId;
        Long expectedValue = 1L;

        // when - valueOperations.get() 호출 시 expectedValue 반환하도록 설정
        when(valueOperations.get(key)).thenReturn(expectedValue);

        // then - 서비스 메서드 호출 및 검증
        boolean isAbusing = watchCacheService.isAbusing(userId, contentPostId);
        assertEquals(true, isAbusing);
    }

//    @Test
//    public void 포스트_뷰_업데이트하기() {
//        // given
//        UserViewLog userViewLog = new UserViewLog(1L, 101L, LocalDateTime.now());
//
//        // when - 어뷰징 상태일 경우
//        when(valueOperations.get("isAbusing:" + userViewLog.getUserId() + ":" + userViewLog.getContentPostId())).thenReturn(1L);
//
//        // then - 어뷰징 상태일 때 뷰 증가하지 않음
//        watchCacheService.updatePostView(userViewLog);
//        verify(hashOperations, never()).increment(anyString(), anyString(), anyLong());
//
//        // when - 어뷰징 상태가 아닐 경우
//        when(valueOperations.get("isAbusing:" + userViewLog.getUserId() + ":" + userViewLog.getContentPostId())).thenReturn(null);
//        watchCacheService.updatePostView(userViewLog);
//
//        // then - 뷰 증가 검증
//        String key = "view:" + userViewLog.getPlaybackDateTime().format(DATE_TIME_FORMATTER);
//        verify(hashOperations, times(1)).increment(key, String.valueOf(userViewLog.getContentPostId()), 1L);
//    }

    @Test
    public void 포스트_뷰_증가하기() {
        // given
        Long userId = 1L;
        Long contentPostId = 101L;
        LocalDateTime viewDateTime = LocalDateTime.now();
        String key = "view:" + viewDateTime.format(DATE_TIME_FORMATTER);

        // when - 서비스 메서드 호출
        watchCacheService.incrementPostView(userId, contentPostId, viewDateTime);

        // then - hashOperations.increment() 호출 검증
        verify(hashOperations, times(1)).increment(key, String.valueOf(contentPostId), 1L);
    }
}
