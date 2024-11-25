package com.example.streaming.cumulativeContentStatistics.repository;

import com.example.streaming.common.entity.CumulativeContentStatisticsEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CumulativeContentStatisticsJpaRepository extends JpaRepository<CumulativeContentStatisticsEntity, Long> {

    @Query(value = "SELECT c FROM CumulativeContentStatisticsEntity c WHERE c.contentPostId IN :contentPostIds ORDER BY c.id")
    Page<CumulativeContentStatisticsEntity> findAllPaging(@Param("contentPostIds") Set<Long> contentPostIds, Pageable pageable);

    List<CumulativeContentStatisticsEntity> findAllByContentPostIdIn(Set<Long> contentPostIds);

    Optional<CumulativeContentStatisticsEntity> findTop1ByContentPostId(Long contentPostId);
}
