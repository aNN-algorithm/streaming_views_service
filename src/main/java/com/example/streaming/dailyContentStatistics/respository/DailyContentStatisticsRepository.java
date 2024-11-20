package com.example.streaming.dailyContentStatistics.respository;

import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;

import java.util.List;
import java.util.Set;

public interface DailyContentStatisticsRepository {

    void update(DailyContentStatistics dailyContentStatistics);

    List<DailyContentStatistics> findAllByContentPostIdInAndDate(Set<Long> contentPostIds, String date);
}
