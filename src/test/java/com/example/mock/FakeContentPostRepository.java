package com.example.mock;

import com.example.streaming.model.ContentPost;
import com.example.streaming.repository.ContentPostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakeContentPostRepository implements ContentPostRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<ContentPost> data = new ArrayList<>();


    @Override
    public ContentPost save(ContentPost contentPost) {
        if (contentPost.getId() == null || contentPost.getId() == 0) {
            ContentPost newContentPost = ContentPost.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .creatorId(contentPost.getCreatorId())
                    .adId(contentPost.getAdId())
                    .name(contentPost.getName())
                    .totalViews(contentPost.getTotalViews())
                    .totalAdViews(contentPost.getTotalAdViews())
                    .totalPlayTime(contentPost.getTotalPlayTime())
                    .isPublic(contentPost.isPublic())
                    .isDeleted(contentPost.isDeleted())
                    .deletedAt(null)
                    .build();
            data.add(newContentPost);
            return newContentPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), contentPost.getId()));
            data.add(contentPost);
            return contentPost;
        }
    }
}
