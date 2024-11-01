package com.example.streaming.watchContent.service;

import com.example.streaming.watchContent.model.UserViewLog;

import java.time.LocalDateTime;

public interface WatchCacheService {

    Long getLastPlayedAt(Long userId, Long contentPostId);

    void updateLastPlayedAt(Long userId, Long contentPostId, Long lastPlayedAt);

    void updatePostView(UserViewLog userViewLog);

    void incrementPostView(Long userId, Long contentPostId, LocalDateTime viewDateTime);

    boolean isAbusing(Long userId, Long contentPostId);

    void cacheAbuseDetectionData(Long userId, Long contentPostId);
}
