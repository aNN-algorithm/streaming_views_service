package com.example.mock.mockRepository;

import com.example.streaming.watchContent.model.UserViewLog;
import com.example.streaming.watchContent.repository.WatchContentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeWatchContentRepository implements WatchContentRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<UserViewLog> data = new ArrayList<>();

    @Override
    public Long getOrCreateLastPlayedAt(Long userId, Long contentPostId) {

        for (UserViewLog userViewLog : data) {
            if (userViewLog.getUserId().equals(userId) && userViewLog.getContentPostId().equals(contentPostId)) {
                // 기존 기록이 있을 경우 lastPlayedAt 반환
                return userViewLog.getLastPlayedAt();
            }
        }

        return 0L;
    }

    @Override
    public void save(UserViewLog userViewLog) {
        if (userViewLog.getId() == null || userViewLog.getId() == 0) {
            UserViewLog newUserViewLog = UserViewLog.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .userId(userViewLog.getUserId())
                    .contentPostId(userViewLog.getContentPostId())
                    .adId(userViewLog.getAdId())
                    .playbackStartAt(userViewLog.getPlaybackStartAt())
                    .playbackEndAt(userViewLog.getPlaybackEndAt())
                    .playbackDateTime(userViewLog.getPlaybackDateTime())
                    .totalPlaybackTime(userViewLog.getTotalPlaybackTime())
                    .lastPlayedAt(userViewLog.getLastPlayedAt())
                    .build();
            data.add(newUserViewLog);
        } else {
            data.removeIf(item -> item.getId().equals(userViewLog.getId()));
            data.add(userViewLog);
        }
    }

    public List<UserViewLog> getAllData() {
        return data;
    }
}
