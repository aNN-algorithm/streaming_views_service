package com.example.streaming.cumulativeContentStatistics.repository;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CumulativeContentStatisticsRepositoryImpl implements CumulativeContentStatisticsRepository {

    private final CumulativeContentStatisticsJpaRepository cumulativeContentStatisticsJpaRepository;

    @Override
    public CumulativeContentStatistics save(CumulativeContentStatistics cumulativeContentStatistics) {
        return cumulativeContentStatisticsJpaRepository.save(CumulativeContentStatisticsEntity.from(cumulativeContentStatistics)).toModel();
    }
}
