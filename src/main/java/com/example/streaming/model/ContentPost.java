package com.example.streaming.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentPost {

    private static final Long CREATOR_ID = 1L; // check : 로그인 구현 후 삭제
    private static final Long INITIAL_VIEWS = 0L;
    private static final Long INITIAL_TIME = 0L;

    private final Long id; // 불변의 객체는 final
    private final Long creatorId;
    private final String adId;
    private final String name;
    private final Long totalViews;
    private final Long totalAdViews;
    private final Long totalPlayTime;
    private final LocalDateTime createdAt;
    private final boolean isPublic;
    private final boolean isDeleted;
    private final LocalDateTime deletedAt;

    @Builder
    public ContentPost(
            Long id,
            Long creatorId,
            String adId,
            String name,
            Long totalViews,
            Long totalAdViews,
            Long totalPlayTime,
            LocalDateTime createdAt,
            boolean isPublic,
            boolean isDeleted,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.creatorId = creatorId;
        this.adId = adId;
        this.name = name;
        this.totalViews = totalViews;
        this.totalAdViews = totalAdViews;
        this.totalPlayTime = totalPlayTime;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public static ContentPost from(ContentPostCreate request) {
        return ContentPost.builder()
                .creatorId(CREATOR_ID)
                .adId(request.getAdId())
                .name(request.getName())
                .totalViews(INITIAL_VIEWS)
                .totalAdViews(INITIAL_VIEWS)
                .totalPlayTime(INITIAL_TIME)
                .createdAt(LocalDateTime.now())
                .isPublic(request.isPublic())
                .isDeleted(false)
                .build();
    }
}
