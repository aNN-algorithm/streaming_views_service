package com.example.streaming.userViewLog.repository;

import com.example.streaming.common.entity.UserViewLogEntity;

import java.util.List;

public interface UserViewLogRepository {

    List<UserViewLogEntity> findLogByIdAndDate(Long contentPostId);
}
