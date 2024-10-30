package com.example.streaming.watchContent.service;

public interface WatchCacheService {

    Long getLastPlayedAt(Long userId, Long contentPostId);

    void updateLastPlayedAt(Long userId, Long contentPostId, Long lastPlayedAt);
}
