package com.example.streaming.service;

import com.example.streaming.model.ContentPost;
import com.example.streaming.model.ContentPostCreate;

public interface ContentPostService {

    public ContentPost create(ContentPostCreate request);
}
