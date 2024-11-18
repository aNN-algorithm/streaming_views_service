package com.example.streaming.common.entity;

import com.example.streaming.watchContent.model.UserViewLog;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_view_log")
@Getter
public class UserViewLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content_post_id", nullable = false)
    private Long contentPostId;

    @Column(name = "ad_id")
    private String adId;

    @Column(name = "playback_start_at", nullable = false)
    private Long playbackStartAt; // 시청 시작한 시간

    @Column(name = "playback_end_at", nullable = false)
    private Long playbackEndAt; // 시청 끝난 시간

    @Column(name = "playback_datetime", nullable = false)
    private LocalDateTime playbackDatetime; // 시청한 시간 -> 서버시간이 아니라 요청한 시간...
    // 30초를 더한다는 건?

    @Column(name = "total_playback_time", nullable = false)
    private Long totalPlaybackTime; // 총 시청 시간

    @Column(name = "last_played_at", nullable = false)
    private Long lastPlayedAt;

    public static UserViewLogEntity from(UserViewLog userViewLog) {
        UserViewLogEntity userViewLogEntity = new UserViewLogEntity();
        userViewLogEntity.id = userViewLog.getId();
        userViewLogEntity.userId = userViewLog.getUserId();
        userViewLogEntity.contentPostId = userViewLog.getContentPostId();
        userViewLogEntity.adId = userViewLog.getAdId();
        userViewLogEntity.playbackStartAt = userViewLog.getPlaybackStartAt();
        userViewLogEntity.playbackEndAt = userViewLog.getPlaybackEndAt();
        userViewLogEntity.playbackDatetime = userViewLog.getPlaybackDateTime();
        userViewLogEntity.totalPlaybackTime = userViewLog.getTotalPlaybackTime();
        userViewLogEntity.lastPlayedAt = userViewLog.getLastPlayedAt();
        return userViewLogEntity;
    }

    public UserViewLog toModel() {
        return UserViewLog.builder()
                .id(id)
                .userId(userId)
                .contentPostId(contentPostId)
                .adId(adId)
                .playbackStartAt(playbackStartAt)
                .playbackEndAt(playbackEndAt)
                .playbackDateTime(playbackDatetime)
                .totalPlaybackTime(totalPlaybackTime)
                .lastPlayedAt(lastPlayedAt)
                .build();
    }
}
