package com.example.streaming.userViewLog.repository;

import com.example.streaming.common.entity.UserViewLogEntity;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserViewLogRepository {

    List<UserViewLogEntity> findLogByIdAndDate(Long contentPostId, Long lastId, PageRequest pageable, String date);
}
