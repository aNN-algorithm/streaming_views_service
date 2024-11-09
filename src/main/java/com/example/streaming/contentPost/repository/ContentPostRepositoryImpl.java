package com.example.streaming.contentPost.repository;

import com.example.streaming.common.entity.ContentPostEntity;
import com.example.streaming.contentPost.model.ContentPost;
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