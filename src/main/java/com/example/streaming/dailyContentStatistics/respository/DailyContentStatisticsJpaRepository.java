package com.example.streaming.dailyContentStatistics.respository;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DailyContentStatisticsJpaRepository extends JpaRepository<DailyContentStatisticsEntity, Long> {

    @Query(value = "SELECT d FROM DailyContentStatisticsEntity d " +
            "WHERE d.contentPostId IN :contentPostIds " +
            "AND FUNCTION('DATE', d.date) = :date")
    List<DailyContentStatisticsEntity> findAllByContentPostIdInAndDate(
            @Param("contentPostIds") Set<Long> contentPostIds,
            @Param("date") String date);
}
