package com.example.streaming.watchContent.service;

public interface WatchContentService {

    Long getLastPlayedAt(Long userId, Long contentPostId);
}
