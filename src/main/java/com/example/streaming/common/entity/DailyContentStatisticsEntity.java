package com.example.streaming.common.entity;

import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_statistics")
public class DailyContentStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_post_id", nullable = false)
    private Long contentPostId;

    @Column(name = "daily_views", nullable = false)
    private Long dailyViews;

    @Column(name = "daily_ad_views", nullable = false)
    private Long dailyAdViews;

    @Column(name = "daily_revenue", nullable = false)
    private Long dailyRevenue;

    @Column(name = "daily_ad_revenue", nullable = false)
    private Long dailyAdRevenue;

    @Column(name = "daily_playback_time", nullable = false)
    private Long dailyPlaybackTime;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public static DailyContentStatisticsEntity from(DailyContentStatistics dailyContentStatistics) {
        DailyContentStatisticsEntity dailyContentStatisticsEntity = new DailyContentStatisticsEntity();
        dailyContentStatisticsEntity.contentPostId = dailyContentStatistics.getContentPostId();
        dailyContentStatisticsEntity.dailyViews = dailyContentStatistics.getDailyViews();
        dailyContentStatisticsEntity.dailyAdViews = dailyContentStatistics.getDailyAdViews();
        dailyContentStatisticsEntity.dailyRevenue = dailyContentStatistics.getDailyRevenue();
        dailyContentStatisticsEntity.dailyAdRevenue = dailyContentStatistics.getDailyAdRevenue();
        dailyContentStatisticsEntity.dailyPlaybackTime = dailyContentStatistics.getDailyPlaybackTime();
        dailyContentStatisticsEntity.date = dailyContentStatistics.getDate();
        return dailyContentStatisticsEntity;
    }

    public DailyContentStatistics toModel() {
        return DailyContentStatistics.builder()
                .id(id)
                .contentPostId(contentPostId)
                .dailyViews(dailyViews)
                .dailyAdViews(dailyAdViews)
                .dailyRevenue(dailyRevenue)
                .dailyAdRevenue(dailyAdRevenue)
                .dailyPlaybackTime(dailyPlaybackTime)
                .date(date)
                .build();
    }
}
