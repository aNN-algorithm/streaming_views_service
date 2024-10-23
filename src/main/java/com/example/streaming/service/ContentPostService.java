package com.example.streaming.service;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.dto.ContentPostCreate;

public interface ContentPostService {

    public ContentPost create(ContentPostCreate request);
}
