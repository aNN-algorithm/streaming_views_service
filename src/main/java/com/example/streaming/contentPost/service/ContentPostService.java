package com.example.streaming.contentPost.service;

import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.model.ContentPostCreate;

public interface ContentPostService {

    public ContentPost create(ContentPostCreate request);
}