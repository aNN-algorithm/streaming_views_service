package com.example.streaming.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentPostCreate {

    private String name;
    private String adId;
    private boolean isPublic;
}
