package com.example.streaming.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentPostCreate {

    private String name;
    private String adId;
    private boolean isPublic;
}
