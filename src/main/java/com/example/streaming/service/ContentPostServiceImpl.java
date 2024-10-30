package com.example.streaming.service;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.service.port.ContentPostRepository;
import com.example.streaming.controller.port.ContentPostService;
import com.example.streaming.controller.request.ContentPostCreate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ContentPostServiceImpl implements ContentPostService {

    private final ContentPostRepository contentPostRepository;

    @Override
    public ContentPost create(ContentPostCreate request) {
        ContentPost contentPost = ContentPost.from(request);
        return contentPostRepository.save(contentPost);
    }
}