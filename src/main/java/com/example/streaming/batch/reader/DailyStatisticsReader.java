//package com.example.streaming.batch.read;
//
//import com.example.streaming.common.entity.DailyContentStatisticsEntity;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.context.annotation.Bean;
//
//import java.io.IOException;
//
//public class DailyStatisticsReader {
//
//    @Bean
//    public ItemReader<Long> customItemReader() {
//        return new CustomItemReader1();
//    }
//
//    @Bean
//    public ItemReader<DailyContentStatisticsEntity> dailyStatisticsReader() throws IOException {
//        DailyStatisticsReader<DailyContentStatisticsEntity> dailyStatisticsReader = new DailyStatisticsReader<>();
//    }
//}
