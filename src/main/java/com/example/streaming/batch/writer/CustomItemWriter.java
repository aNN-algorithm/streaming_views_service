package com.example.streaming.batch.writer;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import com.example.streaming.common.entity.UserViewLogEntity;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import com.example.streaming.dailyContentStatistics.respository.DailyContentStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomItemWriter implements ItemWriter<DailyContentStatistics> {

    private final DailyContentStatisticsRepository dailyContentStatisticsRepository;

    @Override
    public void write(Chunk<? extends DailyContentStatistics> chunk) throws Exception {
        log.info("write");

        for (DailyContentStatistics dailyContentStatistics : chunk) {
            log.info("Writing item: {} {} {}", dailyContentStatistics.getContentPostId(), dailyContentStatistics.getDailyViews(), dailyContentStatistics.getDailyPlaybackTime());
            dailyContentStatisticsRepository.update(dailyContentStatistics);
        }
    }
}
