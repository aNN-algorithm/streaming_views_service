package com.example.streaming.service.port;

import com.example.streaming.domain.ContentPost;

public interface ContentPostRepository {

    ContentPost save(ContentPost contentPost);

}
