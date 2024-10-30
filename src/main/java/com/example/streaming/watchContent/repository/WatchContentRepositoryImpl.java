package com.example.streaming.watchContent.repository;

import com.example.streaming.common.entity.UserViewLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WatchContentRepositoryImpl implements WatchContentRepository {

    private final WatchContentJpaRepository watchContentJpaRepository;


    @Override
    public Long getOrCreateLastPlayedAt(Long userId, Long contentPostId) {

        // TODO 전체 객체 vs 객체의 일부 필드
        Long lastPlayedAt1 = watchContentJpaRepository.findTop1ByUserIdAndContentPostIdOrderByPlaybackDatetimeDesc(userId, contentPostId)
                .map(UserViewLogEntity::getLastPlayedAt).orElse(0L);

        Long lastPlayedAt2 = watchContentJpaRepository.findLastPlayedAtByUserIdAndContentPostId(userId, contentPostId).orElse(0L);

        return lastPlayedAt1;
    }
}
