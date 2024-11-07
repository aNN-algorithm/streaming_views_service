package com.example.streaming.cumulativeContentStatistics.service;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.cumulativeContentStatistics.repository.CumulativeContentStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CumulativeContentServiceImpl implements CumulativeContentService {

    private final CumulativeContentStatisticsRepository cumulativeContentStatisticsRepository;

    @Override
    public CumulativeContentStatistics create(Long contentPostId) {

        CumulativeContentStatistics cumulativeContentStatistics = CumulativeContentStatistics.from(contentPostId);

        return cumulativeContentStatisticsRepository.save(cumulativeContentStatistics);
    }
}
