package com.example.streaming.batch.reader;

import com.example.streaming.dailyContentStatistics.model.DailyContentStatistics;
import com.example.streaming.dailyContentStatistics.respository.DailyContentStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class CustomCumulativeItemReader implements ItemReader<DailyContentStatistics> {

    private final RedisTemplate<String, Object> redisTemplateContentPostId;
    private final DailyContentStatisticsRepository dailyContentStatisticsRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LinkedList<DailyContentStatistics> currentList = new LinkedList<>();
    Set<Long> contentPostIds = new HashSet<>();

    private boolean hasNextPage = true;

    private int redisPage = 0;  // Redis 페이지 인덱스
    private static final int CHUNK_SIZE = 10;  // 청크 크기 설정

    @Value("#{jobParameters['date']}")
    private String date;

    @Override
    public DailyContentStatistics read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (currentList.isEmpty()) {
            loadNextPageFromRedisAndDB();
        }

        if (currentList.isEmpty()) {
            return null;
        }

        log.info("Read item : {}", currentList.getFirst());
        return currentList.removeFirst();
    }

    private void loadNextPageFromRedisAndDB() {
        LocalDate localDate = LocalDate.parse(date);
        String key = "DailyViews:" + localDate.format(DATE_FORMATTER);

        // Redis에서 청크 크기만큼 데이터를 가져오기
        Set<Object> redisData = redisTemplateContentPostId.opsForZSet().range(key, redisPage * CHUNK_SIZE, (redisPage + 1) * CHUNK_SIZE - 1);
        redisPage++;  // 다음 청크를 위해 페이지 인덱스를 증가

        if (redisData == null || redisData.isEmpty()) {
            hasNextPage = false;
        }

        contentPostIds = redisData.stream()
                .map(item -> (Long) item)
                .collect(Collectors.toSet());

        log.info("contentPostIds: {}", contentPostIds);

        List<DailyContentStatistics> pagingList = dailyContentStatisticsRepository.findAllByContentPostIdInAndDate(contentPostIds, localDate.format(DATE_FORMATTER));

        log.info("paging size: {}", pagingList.size());
        log.info("pagingList: {}", pagingList);

        if (pagingList != null || !pagingList.isEmpty()) {
            currentList.addAll(pagingList);
        }
    }
}