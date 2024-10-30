package com.example.streaming.watchContent.service;

import com.example.streaming.watchContent.repository.WatchContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchContentServiceImpl implements WatchContentService {

    private final WatchCacheService watchCacheService;
    private final WatchContentRepository watchContentRepository;

    @Override
    public Long getLastPlayedAt(Long userId, Long contentPostId) {

        // (Cache Hit) Redis 에서 먼저 데이터 확인하기
        Long lastPlayedAt = watchCacheService.getLastPlayedAt(userId, contentPostId);
        if (lastPlayedAt != null) {
            log.info("Cache Hit! lastPlayedAt = {}", lastPlayedAt);
        } else {
        // (Cache Miss) DB에서 마지막 시청 지점 가져오기
            log.info("Cache Miss! Start to search lastPlayedAt in Database");
            lastPlayedAt = watchContentRepository.getOrCreateLastPlayedAt(userId, contentPostId);
            log.info("lastPlayedAt in Database = {}", lastPlayedAt);
            watchCacheService.updateLastPlayedAt(userId, contentPostId, lastPlayedAt);
        }

        return lastPlayedAt;
    }
}
