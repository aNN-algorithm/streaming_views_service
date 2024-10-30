package com.example.streaming.watchContent.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserViewLogCreate {

    private Long playbackStartAt;
    private Long playbackEndAt;
    private Long lastPlayedAt;
}