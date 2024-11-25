package com.example.streaming.batch.processor;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.cumulativeContentStatistics.repository.CumulativeContentStatisticsRepository;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class CustomCumulativeItemProcessor implements ItemProcessor<DailyContentStatistics, CumulativeContentStatistics> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final CumulativeContentStatisticsRepository cumulativeContentStatisticsRepository;

    @Value("#{jobParameters['date']}")
    private String date;

    @Override
    public CumulativeContentStatistics process(DailyContentStatistics item) throws Exception {

        log.info("CumulativeContent Batch : {}", item.getContentPostId());

        CumulativeContentStatistics cumulativeData = cumulativeContentStatisticsRepository.findTop1ByContentPostId(item.getContentPostId());

        CumulativeContentStatistics newData = CumulativeContentStatistics.from(
                cumulativeData.getId(),
                cumulativeData.getContentPostId(),
                cumulativeData.getCumulativeViews() + item.getDailyViews(),
                cumulativeData.getCumulativeAdViews() + item.getDailyAdViews(),
                cumulativeData.getCumulativeRevenue() + item.getDailyRevenue(),
                cumulativeData.getCumulativeAdRevenue() + item.getDailyAdRevenue(),
                cumulativeData.getCumulativePlaybackTime() + item.getDailyPlaybackTime(),
                LocalDateTime.now()
        );

        return newData;
    }
}
