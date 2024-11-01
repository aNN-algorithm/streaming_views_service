package com.example.streaming.watchContent.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserViewLog {

    private Long id;
    private Long userId;
    private Long contentPostId;
    private String adId;
    private Long playbackStartAt;
    private Long playbackEndAt;
    private LocalDateTime playbackDateTime;
    private Long totalPlaybackTime;
    private Long lastPlayedAt;

    @Builder
    public UserViewLog(
            Long id,
            Long userId,
            Long contentPostId,
            String adId,
            Long playbackStartAt,
            Long playbackEndAt,
            LocalDateTime playbackDateTime,
            Long totalPlaybackTime,
            Long lastPlayedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.contentPostId = contentPostId;
        this.adId = adId;
        this.playbackStartAt = playbackStartAt;
        this.playbackEndAt = playbackEndAt;
        this.playbackDateTime = playbackDateTime;
        this.totalPlaybackTime = totalPlaybackTime;
        this.lastPlayedAt = lastPlayedAt;
    }

    public static UserViewLog create(Long userId, Long contentPostId, UserViewLogCreate userViewLogCreate) {
        return UserViewLog.builder()
                .userId(userId)
                .contentPostId(contentPostId)
                .playbackStartAt(userViewLogCreate.getPlaybackStartAt())
                .playbackEndAt(userViewLogCreate.getPlaybackEndAt())
                .playbackDateTime(LocalDateTime.now()) // TODO : 요청에, 시작 시간(get요청)에 + 30초를 더한,이 될 거 -> 조회시간을 저장
                .totalPlaybackTime(userViewLogCreate.getPlaybackEndAt() - userViewLogCreate.getPlaybackStartAt())
                .lastPlayedAt(userViewLogCreate.getLastPlayedAt())
                .build();
    }
}