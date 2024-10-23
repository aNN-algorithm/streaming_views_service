package com.example.streaming.controller.port;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.controller.request.ContentPostCreate;

public interface ContentPostService {

    public ContentPost create(ContentPostCreate request);
}
