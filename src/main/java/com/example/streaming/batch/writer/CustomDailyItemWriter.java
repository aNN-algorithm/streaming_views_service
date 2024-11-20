package com.example.streaming.batch.writer;

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
public class CustomDailyItemWriter implements ItemWriter<DailyContentStatistics> {

    private final DailyContentStatisticsRepository dailyContentStatisticsRepository;

    @Override
    public void write(Chunk<? extends DailyContentStatistics> chunk) throws Exception {
        log.info("write");

        //TODO: 배치 인서트 넣고 성능 비교해볼 것
        // Write는 한 번밖에 일어나지 않으니까 Write 전후로 시간 비교
        // 여기서 정의해도 되고 공통 리스너에 해도 됨
        for (DailyContentStatistics dailyContentStatistics : chunk) {
            log.info("Writing item: {} {} {}", dailyContentStatistics.getContentPostId(), dailyContentStatistics.getDailyViews(), dailyContentStatistics.getDailyPlaybackTime());
            dailyContentStatisticsRepository.update(dailyContentStatistics);
        }
    }
}
