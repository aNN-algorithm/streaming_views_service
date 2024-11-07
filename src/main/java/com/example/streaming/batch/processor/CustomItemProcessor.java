package com.example.streaming.batch.processor;

import com.example.streaming.common.entity.DailyContentStatisticsEntity;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Long, DailyContentStatisticsEntity> {
    @Override
    public DailyContentStatisticsEntity process(Long item) throws Exception {

        return null;
    }
}
