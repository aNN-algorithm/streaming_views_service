package com.example.streaming.cumulativeContentStatistics.service;

import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;

public interface CumulativeContentService {

    CumulativeContentStatistics create(Long contentPostId);
}
