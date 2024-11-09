package com.example.streaming.cumulativeContentStatistics.repository;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;

import java.util.List;
import java.util.Set;

public interface CumulativeContentStatisticsRepository {

    CumulativeContentStatistics save(CumulativeContentStatistics cumulativeContentStatistics);

    List<CumulativeContentStatistics> findAllPaging(Set<Long> contentPostIds, int currentPage, int pageSize);

    List<CumulativeContentStatistics> findAllByIdIn(Set<Long> contentPostIds);

}
