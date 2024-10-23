package com.example.streaming.repository.impl;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.domain.ContentPostEntity;
import com.example.streaming.repository.ContentPostJpaRepository;
import com.example.streaming.repository.ContentPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentPostRepositoryImpl implements ContentPostRepository {

    private final ContentPostJpaRepository contentPostJpaRepository;

    @Override
    public ContentPost save(ContentPost contentPost) {
        return contentPostJpaRepository.save(ContentPostEntity.from(contentPost)).toModel();
    }
}