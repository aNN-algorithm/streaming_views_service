package com.example.streaming.contentPost.service;

import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.repository.ContentPostRepository;
import com.example.streaming.contentPost.model.ContentPostCreate;
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