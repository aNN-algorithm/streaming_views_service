package com.example.streaming.service;

import com.example.streaming.model.ContentPost;
import com.example.streaming.repository.ContentPostRepository;
import com.example.streaming.model.ContentPostCreate;
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