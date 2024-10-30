package com.example.streaming.watchContent.repository;

public interface WatchContentRepository {

    Long getOrCreateLastPlayedAt(Long userId, Long contentPostId);
}
