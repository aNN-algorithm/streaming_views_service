package com.example.streaming.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "content_post")
public class ContentPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

//    @Type(ListArrayType.class)
//    @Column(name = "ad_id", columnDefinition = "text[]")
//    private String[] adId;
    @Column(name = "ad_id", nullable = false)
    private String adId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_views", nullable = false)
    private Long totalViews;

    @Column(name = "total_ad_views", nullable = false)
    private Long totalAdViews;

    @Column(name = "total_play_time", nullable = false)
    private Long totalPlayTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static ContentPostEntity from(ContentPost contentPost) {
        ContentPostEntity contentPostEntity = new ContentPostEntity();
        contentPostEntity.id = contentPost.getId();
        contentPostEntity.creatorId = contentPost.getCreatorId();
        contentPostEntity.adId = contentPost.getAdId();
        contentPostEntity.name = contentPost.getName();
        contentPostEntity.totalViews = contentPost.getTotalViews();
        contentPostEntity.totalAdViews = contentPost.getTotalAdViews();
        contentPostEntity.totalPlayTime = contentPost.getTotalPlayTime();
        contentPostEntity.createdAt = contentPost.getCreatedAt();
        contentPostEntity.isPublic = contentPost.isPublic();
        contentPostEntity.isDeleted = contentPost.isDeleted();
        contentPostEntity.deletedAt = contentPost.getDeletedAt();
        return contentPostEntity;
    }

    public ContentPost toModel() {
        return ContentPost.builder()
                .creatorId(creatorId)
                .adId(adId)
                .name(name)
                .totalViews(totalViews)
                .totalAdViews(totalAdViews)
                .totalPlayTime(totalPlayTime)
                .createdAt(createdAt)
                .isPublic(isPublic)
                .isDeleted(isDeleted)
                .deletedAt(deletedAt)
                .build();
    }
}
