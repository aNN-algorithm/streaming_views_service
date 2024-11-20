package com.example.streaming.batch.writer;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.cumulativeContentStatistics.repository.CumulativeContentStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCumulativeItemWriter implements ItemWriter<CumulativeContentStatistics> {

    private final CumulativeContentStatisticsRepository cumulativeContentStatisticsRepository;

    @Override
    public void write(Chunk<? extends CumulativeContentStatistics> chunk) throws Exception {

        //TODO 배치 인서트
        for (CumulativeContentStatistics cumulativeContentStatistics : chunk) {
            cumulativeContentStatisticsRepository.save(cumulativeContentStatistics);
        }
    }
}
