package com.example.streaming.batch.reader;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.cumulativeContentStatistics.repository.CumulativeContentStatisticsRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class CustomItemReader implements ItemReader<CumulativeContentStatistics> {

    private final RedisTemplate<String, Object> redisTemplateContentPostId;
    private final CumulativeContentStatisticsRepository cumulativeContentStatisticsRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LinkedList<CumulativeContentStatistics> currentList = new LinkedList<>();
    Set<Long> contentPostIds = new HashSet<>();

    private long cursor = 0L;
    private long chunkSize = 3;
    private boolean hasNextPage = true;

    @Value("#{jobParameters['date']}")
    private String date;

    @Value("#{stepExecutionContext['start']}")
    private Long start;

    @Value("#{stepExecutionContext['end']}")
    private Long end;

    @Override
    public CumulativeContentStatistics read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (start == 0 && end == -1) {
            log.info("No data in this range, start : {}, end : {}", start, end);
            return null;
        }

        // currentList가 비어있다면 Redis에서 새 페이지 데이터를 가져오고 DB에서 조회
        if (currentList.isEmpty() && hasNextPage) {
            loadNextPageFromRedisAndDB();
        }

        // currentList에 데이터가 더 이상 없으면 null 반환
        if (currentList.isEmpty()) {
            return null;
        }

        // currentList에서 하나의 데이터를 꺼내어 반환
        log.info("return item : {}", currentList.get(0).getContentPostId());
        return currentList.removeFirst();
    }

    private void loadNextPageFromRedisAndDB() {
        // Redis에서 지정된 페이지의 데이터를 가져옴
        //LocalDate localDate = LocalDate.parse(date).minusDays(1);
        LocalDate localDate = LocalDate.parse(date).minusDays(2);
        String key = "DailyViews:" + localDate.format(DATE_FORMATTER);
        log.info("Fetching contentPostIds from Redis for key: {}", key);

        cursor = start + chunkSize;
        if (cursor > end) {
            cursor = end;
        }
        log.info("start : {}, cursor : {}, end : {}", start, cursor, end);
        Set<Object> redisData = redisTemplateContentPostId.opsForZSet()
                .range(key, start, cursor);
        start += chunkSize;

        log.info("redis data size: {}, ids : {}", redisData.size(), redisData);

        //TODO: 커서로 가져올 때 커서가 엔드보다 크거나 같아지면, 다 가져온 것
        if (redisData == null || cursor >= end) {
            hasNextPage = false;
        }

        // Redis에서 가져온 데이터를 contentPostIds에 추가
        contentPostIds = redisData.stream()
                .map(item -> (Long) item)
                .collect(Collectors.toSet());


        log.info("Found {} items in Redis for key: {}", contentPostIds.size(), key);

        // DB에서 Redis에서 가져온 contentPostIds에 해당하는 데이터 조회
        List<CumulativeContentStatistics> pagingList = cumulativeContentStatisticsRepository.findAllByContentPostIdIn(contentPostIds);
        log.info("pagingList size: {}", pagingList.size());
        for (CumulativeContentStatistics cumulativeContentStatistics : pagingList) {
            log.info("contentPostIds : {}, 누적테이블 content post id : {}", contentPostIds, cumulativeContentStatistics.getContentPostId());
        }

        if (pagingList != null && !pagingList.isEmpty()) {
            currentList.addAll(pagingList); // 조회 결과를 currentList에 저장
        }
    }

}