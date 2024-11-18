package com.example.streaming.cumulativeContentStatistics.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CumulativeContentStatistics {

    private static final Long INITIAL_VALUE = 0L;

    private final Long id;
    private final Long contentPostId;
    private final Long cumulativeViews;
    private final Long cumulativeAdViews;
    private final Long cumulativeRevenue;
    private final Long cumulativeAdRevenue;
    private final Long cumulativePlaybackTime;
    private final LocalDateTime updatedAt;

    @Builder
    public CumulativeContentStatistics(
            Long id,
            Long contentPostId,
            Long cumulativeViews,
            Long cumulativeAdViews,
            Long cumulativeRevenue,
            Long cumulativeAdRevenue,
            Long cumulativePlaybackTime,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.contentPostId = contentPostId;
        this.cumulativeViews = cumulativeViews;
        this.cumulativeAdViews = cumulativeAdViews;
        this.cumulativeRevenue = cumulativeRevenue;
        this.cumulativeAdRevenue = cumulativeAdRevenue;
        this.cumulativePlaybackTime = cumulativePlaybackTime;
        this.updatedAt = updatedAt;
    }

    public static CumulativeContentStatistics from(Long contentPostId) {
        return CumulativeContentStatistics.builder()
                .contentPostId(contentPostId)
                .cumulativeViews(INITIAL_VALUE)
                .cumulativeAdViews(INITIAL_VALUE)
                .cumulativeRevenue(INITIAL_VALUE)
                .cumulativeAdRevenue(INITIAL_VALUE)
                .cumulativePlaybackTime(INITIAL_VALUE)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
