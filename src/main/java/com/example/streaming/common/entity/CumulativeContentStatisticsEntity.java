package com.example.streaming.common.entity;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "cumulative_content_statistics")
public class CumulativeContentStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_post_id", nullable = false)
    private Long contentPostId;

    @Column(name = "cumulative_views", nullable = false)
    private Long cumulativeViews;

    @Column(name = "cumulative_ad_views", nullable = false)
    private Long cumulativeAdViews;

    @Column(name = "cumulative_revenue", nullable = false)
    private Long cumulativeRevenue;

    @Column(name = "cumulative_ad_revenue", nullable = false)
    private Long cumulativeAdRevenue;

    @Column(name = "cumulative_playback_time", nullable = false)
    private Long cumulativePlaybackTime;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static CumulativeContentStatisticsEntity from(CumulativeContentStatistics cumulativeContentStatistics) {
        CumulativeContentStatisticsEntity cumulativeContentStatisticsEntity = new CumulativeContentStatisticsEntity();
        cumulativeContentStatisticsEntity.id = cumulativeContentStatistics.getId();
        cumulativeContentStatisticsEntity.contentPostId = cumulativeContentStatistics.getContentPostId();
        cumulativeContentStatisticsEntity.cumulativeViews = cumulativeContentStatistics.getCumulativeViews();
        cumulativeContentStatisticsEntity.cumulativeAdViews = cumulativeContentStatistics.getCumulativeAdViews();
        cumulativeContentStatisticsEntity.cumulativeRevenue = cumulativeContentStatistics.getCumulativeRevenue();
        cumulativeContentStatisticsEntity.cumulativeAdRevenue = cumulativeContentStatistics.getCumulativeAdRevenue();
        cumulativeContentStatisticsEntity.cumulativePlaybackTime = cumulativeContentStatistics.getCumulativePlaybackTime();
        cumulativeContentStatisticsEntity.updatedAt = LocalDateTime.now();
        return cumulativeContentStatisticsEntity;
    }

    public CumulativeContentStatistics toModel() {
        return CumulativeContentStatistics.builder()
                .id(id)
                .contentPostId(contentPostId)
                .cumulativeViews(cumulativeViews)
                .cumulativeAdViews(cumulativeAdViews)
                .cumulativeRevenue(cumulativeRevenue)
                .cumulativeAdRevenue(cumulativeAdRevenue)
                .cumulativePlaybackTime(cumulativePlaybackTime)
                .updatedAt(updatedAt)
                .build();
    }
}
