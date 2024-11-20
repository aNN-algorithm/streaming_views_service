package com.example.streaming.dailyContentStatistics.respository;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DailyContentStatisticsRepositoryImpl implements DailyContentStatisticsRepository {

    private final DailyContentStatisticsJpaRepository dailyContentStatisticsJpaRepository;

    @Override
    public void update(DailyContentStatistics dailyContentStatistics) {
        dailyContentStatisticsJpaRepository.save(DailyContentStatisticsEntity.from(dailyContentStatistics));
    }

    @Override
    public List<DailyContentStatistics> findAllByContentPostIdInAndDate(Set<Long> contentPostIds, String date) {
        List<DailyContentStatisticsEntity> list = dailyContentStatisticsJpaRepository.findAllByContentPostIdInAndDate(contentPostIds, date);
        log.info("string date : {}", date);
        log.info("DailyContentStatisticsEntity list : {}", list.size());

        return list.stream().map(DailyContentStatisticsEntity::toModel).collect(Collectors.toList());
    }
}
