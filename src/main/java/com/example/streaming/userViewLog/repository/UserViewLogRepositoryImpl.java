package com.example.streaming.userViewLog.repository;

import com.example.streaming.common.entity.UserViewLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserViewLogRepositoryImpl implements UserViewLogRepository {

    private final UserViewLogJpaRepository userViewLogJpaRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<UserViewLogEntity> findLogByIdAndDate(Long contentPostId) {
        String date = LocalDate.now().minusDays(1).format(DATE_FORMATTER);

        log.info("date : {} ", date);

        List<UserViewLogEntity> list = userViewLogJpaRepository.findByContentPostIdAndPlaybackDatetime(
                contentPostId,
                date);

        return list;
    }
}
