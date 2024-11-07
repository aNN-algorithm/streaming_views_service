package com.example.streaming.cumulativeContentStatistics.repository;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CumulativeContentStatisticsJpaRepository extends JpaRepository<CumulativeContentStatisticsEntity, Long> {
}
