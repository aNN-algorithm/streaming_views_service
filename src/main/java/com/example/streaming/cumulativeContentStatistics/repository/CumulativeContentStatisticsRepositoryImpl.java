package com.example.streaming.cumulativeContentStatistics.repository;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CumulativeContentStatisticsRepositoryImpl implements CumulativeContentStatisticsRepository {

    private final CumulativeContentStatisticsJpaRepository cumulativeContentStatisticsJpaRepository;

    @Override
    public CumulativeContentStatistics save(CumulativeContentStatistics cumulativeContentStatistics) {
        return cumulativeContentStatisticsJpaRepository.save(CumulativeContentStatisticsEntity.from(cumulativeContentStatistics)).toModel();
    }

    @Override
    public List<CumulativeContentStatistics> findAllPaging(Set<Long> contentPostIds, int currentPage, int pageSize) {

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<CumulativeContentStatisticsEntity> contentPage = cumulativeContentStatisticsJpaRepository.findAllPaging(contentPostIds, pageable);
        return contentPage.map(CumulativeContentStatisticsEntity::toModel).getContent();
    }

    @Override
    public List<CumulativeContentStatistics> findAllByIdIn(Set<Long> contentPostIds) {

        List<CumulativeContentStatisticsEntity> list = cumulativeContentStatisticsJpaRepository.findAllByIdIn(contentPostIds);

        return list.stream().map(CumulativeContentStatisticsEntity::toModel).collect(Collectors.toList());
    }
}
