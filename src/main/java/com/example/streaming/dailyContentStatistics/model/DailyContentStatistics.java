package com.example.streaming.dailyContentStatistics.model;

import com.example.streaming.watchContent.model.UserViewLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DailyContentStatistics {

    private static final Long INITIAL_VALUE = 0L;

    private Long id;
    private Long contentPostId;
    private Long dailyViews;
    private Long dailyAdViews;
    private Long dailyRevenue;
    private Long dailyAdRevenue;
    private Long dailyPlaybackTime;
    private LocalDateTime date;

    @Builder
    public DailyContentStatistics(
            Long id,
            Long contentPostId,
            Long dailyViews,
            Long dailyAdViews,
            Long dailyRevenue,
            Long dailyAdRevenue,
            Long dailyPlaybackTime,
            LocalDateTime date
    ) {
        this.id = id;
        this.contentPostId = contentPostId;
        this.dailyViews = dailyViews;
        this.dailyAdViews = dailyAdViews;
        this.dailyRevenue = dailyRevenue;
        this.dailyAdRevenue = dailyAdRevenue;
        this.dailyPlaybackTime = dailyPlaybackTime;
        this.date = date;
    }

    //TODO 이렇게 아이디만 매개변수로 가져와도 되나?
    public static DailyContentStatistics from(Long contentPostId,
                                              Long dailyViews,
                                              Long dailyRevenue,
                                              Long dailyPlaybackTime
    ) {
        return DailyContentStatistics.builder()
                .contentPostId(contentPostId)
                .dailyViews(dailyViews)
                .dailyAdViews(INITIAL_VALUE)
                .dailyRevenue(dailyRevenue)
                .dailyAdRevenue(INITIAL_VALUE)
                .dailyPlaybackTime(dailyPlaybackTime)
                .date(LocalDateTime.now())
                .build();
    }
}
