package com.example.streaming.contentPost.service;

import com.example.streaming.common.entity.ContentPostEntity;
import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.repository.ContentPostJpaRepository;
import com.example.streaming.contentPost.repository.ContentPostRepository;
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