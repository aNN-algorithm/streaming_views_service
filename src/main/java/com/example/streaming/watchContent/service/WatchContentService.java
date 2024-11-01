package com.example.streaming.watchContent.service;

import com.example.streaming.watchContent.model.UserViewLog;

public interface WatchContentService {

    Long getLastPlayedAt(Long userId, Long contentPostId);

    void createLog(UserViewLog userViewLog);
}
