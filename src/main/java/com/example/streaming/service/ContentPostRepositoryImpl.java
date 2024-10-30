package com.example.streaming.service;

import com.example.streaming.common.entity.ContentPostEntity;
import com.example.streaming.model.ContentPost;
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