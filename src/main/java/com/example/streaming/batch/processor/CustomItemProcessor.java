package com.example.streaming.batch.processor;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import com.example.streaming.common.entity.UserViewLogEntity;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import com.example.streaming.userViewLog.repository.UserViewLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomItemProcessor implements ItemProcessor<CumulativeContentStatisticsEntity, DailyContentStatistics> {

    private final UserViewLogRepository userViewLogRepository;

    @Override
    public DailyContentStatistics process(CumulativeContentStatisticsEntity item) throws Exception {

        log.info("item : {}, {}, {}", item.getContentPostId(), item.getCumulativeViews(), item.getCumulativeAdViews());

        List<UserViewLogEntity> list = userViewLogRepository.findLogByIdAndDate(item.getContentPostId());

        // 조회수
        Long dailyViews = (long) list.size();

        // 재생 시간
        Long dailyPlaybackTime = list.stream()
                .mapToLong(UserViewLogEntity::getTotalPlaybackTime)
                .sum();

        log.info("dailyViews = {}, dailyPlaybackTime = {}", dailyViews, dailyPlaybackTime);

        // 수익
        // 전체 수익 구하기 : item.getCumulativeViews() + dailyViews 에 대한 수익 구하기
        // 일일 수익 구하기 : 전체 수익 - item.getCumulativeRevenue()
        Long dailyRevenue = 0L;

        DailyContentStatistics dailyContentStatistics = DailyContentStatistics.from(item.getContentPostId(),
                dailyViews,
                dailyRevenue,
                dailyPlaybackTime);

        log.info("processing item: {} {} {}", dailyContentStatistics.getContentPostId(), dailyContentStatistics.getDailyViews(), dailyContentStatistics.getDailyPlaybackTime());

        log.info("processor end line");
        return dailyContentStatistics;
    }
}