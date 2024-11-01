package com.example.streaming.contentPost.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentPostCreate {

    private String name;
    private String adId;
    private boolean isPublic;
}
