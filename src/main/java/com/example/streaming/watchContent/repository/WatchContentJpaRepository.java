package com.example.streaming.watchContent.repository;

import com.example.streaming.common.entity.UserViewLogEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WatchContentJpaRepository extends JpaRepository<UserViewLogEntity, Long> {

    // TODO 전체 객체 vs 객체의 일부 필드
    // 전체 객체를 가져와서 일부 필드만 반환하는 메서드
    Optional<UserViewLogEntity> findTop1ByUserIdAndContentPostIdOrderByPlaybackDatetimeDesc(Long userId, Long contentPostId);

    // 특정 필드만 반환하는 메서드
    @Query("SELECT w.lastPlayedAt " +
            "FROM UserViewLogEntity w " +
            "WHERE w.userId = :userId AND w.contentPostId = :contentPostId " +
            "ORDER BY w.playbackDatetime DESC")
    Optional<Long> findLastPlayedAtByUserIdAndContentPostId(@Param("userId") Long userId, @Param("contentPostId") Long contentPostId);

}
