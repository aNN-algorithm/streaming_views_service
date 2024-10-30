package com.example.streaming.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentPostCreate {

    private String name;
    private String adId;
    private boolean isPublic;
}
