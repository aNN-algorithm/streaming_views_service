package com.example.streaming.watchContent.controller;

import com.example.streaming.watchContent.model.UserViewLog;
import com.example.streaming.watchContent.model.UserViewLogCreate;
import com.example.streaming.watchContent.service.WatchContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/watch")
public class WatchContentController {

    private final WatchContentService watchContentService;

    @GetMapping("/lastPlayedAt/{contentPostId}") // Redis 나 DB 에서 마지막으로 시청한 시점을 가져오는 END POINT
    public ResponseEntity<?> getLastPlayedAt(@PathVariable Long contentPostId) {

        Long userId = 1L;
        Long lastPlayedAt = watchContentService.getLastPlayedAt(userId, contentPostId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status", HttpStatus.OK.value(), "lastPlayedAt", lastPlayedAt));
    }

    @PostMapping("/{contentPostId}") // 시청을 시작한 시간, 끝낸 시간, 그리고 마지막으로 시청한 시점을 저장하는 END POINT
    public ResponseEntity<?> createLog(@PathVariable Long contentPostId,
                                       @RequestBody UserViewLogCreate userViewLogCreate) {
        Long userId = 1L;

        // 어뷰징 판단

        watchContentService.createLog(UserViewLog.create(userId, contentPostId, userViewLogCreate));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
