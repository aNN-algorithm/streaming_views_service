package com.example.streaming.batch.processor;

import com.example.streaming.common.entity.UserViewLogEntity;
import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import com.example.streaming.userViewLog.repository.UserViewLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class CustomItemProcessor implements ItemProcessor<CumulativeContentStatistics, DailyContentStatistics> {

    private final UserViewLogRepository userViewLogRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int pageSize = 3;

    @Value("#{jobParameters['date']}")
    private String date;

    @Override
    public DailyContentStatistics process(CumulativeContentStatistics item) throws Exception {
        LocalDate localDate = LocalDate.parse(date);
        String batchBatch = localDate.format(DATE_FORMATTER);

        log.info("item : {}, {}, {}", item.getContentPostId(), item.getCumulativeViews(), item.getCumulativeAdViews());
        Long lastId = 0L;
        Long dailyViews = 0L;
        Long dailyPlaybackTime = 0L;

        while (true) {

            PageRequest pageable = PageRequest.of(0, pageSize);
            List<UserViewLogEntity> list = userViewLogRepository.findLogByIdAndDate(item.getContentPostId(), lastId, pageable, batchBatch);

            if (list.isEmpty()) {
                break;
            }

            for (UserViewLogEntity userViewLog : list) {
                log.info("현재 item id : {}, log id : {}, user id : {}, playbackTime: {}", item.getContentPostId(), userViewLog.getId(), userViewLog.getUserId(), userViewLog.getTotalPlaybackTime());
                dailyViews += 1; // 각 로그를 한 번의 조회로 간주할 경우, 또는 log.getViewCount()로 조회 수 합산
                dailyPlaybackTime += userViewLog.getTotalPlaybackTime(); // 각 로그의 재생 시간을 합산
            }

            lastId = list.getLast().getId();
        }

        Long dailyRevenue = 0L;
        DailyContentStatistics dailyContentStatistics =
                DailyContentStatistics.from(item.getContentPostId(), dailyViews, dailyRevenue, dailyPlaybackTime);

        log.info("processing item: {} {} {}", dailyContentStatistics.getContentPostId(), dailyContentStatistics.getDailyViews(), dailyContentStatistics.getDailyPlaybackTime());

        log.info("processor end line");
        return dailyContentStatistics;
    }
}