package com.example.streaming.service;

import com.example.mock.FakeWatchContentRepository;
import com.example.streaming.watchContent.model.UserViewLog;
import com.example.streaming.watchContent.model.UserViewLogCreate;
import com.example.streaming.watchContent.service.WatchContentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WatchContentServiceTest {

    private WatchContentServiceImpl watchContentService;
    private FakeWatchContentRepository fakeWatchContentRepository;

    @BeforeEach
    void init() {

        fakeWatchContentRepository = new FakeWatchContentRepository();

        this.watchContentService = WatchContentServiceImpl.builder()
                .watchContentRepository(fakeWatchContentRepository)
                .build();
    }

    @Test
    public void 시청_조회_로그를_생성하기() {
        // given
        Long userId = 1L;
        Long contentPostId = 1L;
        UserViewLogCreate userViewLogCreate = UserViewLogCreate.builder()
                .playbackStartAt(1730282250015L)
                .playbackEndAt(1730282340015L)
                .lastPlayedAt(20L)
                .build();

        // when
        watchContentService.createLog(UserViewLog.create(userId, contentPostId, userViewLogCreate));

        // then
        List<UserViewLog> savedData = fakeWatchContentRepository.getAllData();
        assertNotNull(savedData);
        assertEquals(1, savedData.size());

        UserViewLog savedLog = savedData.getFirst();
        assertEquals(userId, savedLog.getUserId());
        assertEquals(contentPostId, savedLog.getContentPostId());
        assertEquals(20L, savedLog.getLastPlayedAt());
    }

    @Test
    public void DB에서_마지막_시청_지점_가져오기() {
        // given
        Long userId = 2L;
        Long contentPostId = 2L;
        UserViewLog userViewLog1 = UserViewLog.builder()
                .id(2L)
                .userId(2L)
                .contentPostId(2L)
                .adId("1,2,3")
                .playbackStartAt(1730282250015L)
                .playbackEndAt(1730282340015L)
                .playbackDateTime(LocalDateTime.now())
                .totalPlaybackTime(30L)
                .lastPlayedAt(15L)
                .build();
        fakeWatchContentRepository.save(userViewLog1);

        // when
        Long lastPlayedAt = fakeWatchContentRepository.getOrCreateLastPlayedAt(userId, contentPostId);

        // then
        assertEquals(lastPlayedAt, 15L);
    }

    @Test
    public void DB에_마지막_시청_지점이_없을_때_가져오기() {
        // given
        Long userId = 3L;
        Long contentPostId = 3L;

        // when
        Long lastPlayedAt = fakeWatchContentRepository.getOrCreateLastPlayedAt(userId, contentPostId);

        // then
        assertEquals(lastPlayedAt, 0L);
    }
}
