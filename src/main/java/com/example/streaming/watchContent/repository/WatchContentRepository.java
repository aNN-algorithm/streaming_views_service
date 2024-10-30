package com.example.streaming.watchContent.repository;

import com.example.streaming.watchContent.model.UserViewLog;

public interface WatchContentRepository {

    Long getOrCreateLastPlayedAt(Long userId, Long contentPostId);

    void save(UserViewLog userViewLog);
}
