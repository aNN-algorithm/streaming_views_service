package com.example.streaming.infrastructure;

import com.example.streaming.domain.ContentPost;
import com.example.streaming.service.port.ContentPostRepository;
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