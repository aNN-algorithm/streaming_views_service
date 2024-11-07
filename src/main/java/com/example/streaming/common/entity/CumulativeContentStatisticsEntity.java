package com.example.streaming.common.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
}
