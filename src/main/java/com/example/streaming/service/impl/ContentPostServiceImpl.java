package com.example.streaming.service.impl;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.repository.ContentPostRepository;
import com.example.streaming.service.ContentPostService;
import com.example.streaming.dto.ContentPostCreate;
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