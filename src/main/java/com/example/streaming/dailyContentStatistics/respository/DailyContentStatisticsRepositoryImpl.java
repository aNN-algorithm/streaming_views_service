package com.example.streaming.dailyContentStatistics.respository;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DailyContentStatisticsRepositoryImpl implements DailyContentStatisticsRepository {

    private final DailyContentStatisticsJpaRepository dailyContentStatisticsJpaRepository;

    @Override
    public void update(DailyContentStatistics dailyContentStatistics) {
        dailyContentStatisticsJpaRepository.save(DailyContentStatisticsEntity.from(dailyContentStatistics));
    }
}
