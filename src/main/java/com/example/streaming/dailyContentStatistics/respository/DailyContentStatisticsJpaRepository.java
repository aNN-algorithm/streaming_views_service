package com.example.streaming.dailyContentStatistics.respository;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyContentStatisticsJpaRepository extends JpaRepository<DailyContentStatisticsEntity, Long> {
}
