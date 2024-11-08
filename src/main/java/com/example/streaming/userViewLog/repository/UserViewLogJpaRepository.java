package com.example.streaming.userViewLog.repository;

import com.example.streaming.common.entity.UserViewLogEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserViewLogJpaRepository extends JpaRepository<UserViewLogEntity, Long> {

    @Query(value = "SELECT * FROM user_view_log e WHERE e.content_post_id = :contentPostId AND DATE(e.playback_datetime) = :date", nativeQuery = true)
    List<UserViewLogEntity> findByContentPostIdAndPlaybackDatetime(
            @Param("contentPostId") Long contentPostId,
            @Param("date") String date);


}
