package com.example.streaming.service;

import com.example.mock.FakeContentPostRepository;
import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.model.ContentPostCreate;
import com.example.streaming.contentPost.service.ContentPostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContentPostServiceTest {

    private ContentPostServiceImpl contentPostService;

    @BeforeEach
    void init() {

        FakeContentPostRepository fakeContentPostRepository = new FakeContentPostRepository();

        this.contentPostService = ContentPostServiceImpl.builder()
                .contentPostRepository(fakeContentPostRepository)
                .build();
    }

    @Test
    public void ContentPost를_Create() {
        // given
        ContentPostCreate contentPostCreate = ContentPostCreate.builder()
                .name("데이먼스 이어의 플레이리스트")
                .adId("1,2,3")
                .isPublic(true)
                .build();

        // when
        ContentPost contentPost = contentPostService.create(contentPostCreate);

        // then
        assertThat(contentPost.getId()).isNotNull();
        assertThat(contentPost.getCreatorId()).isEqualTo(1L);
        assertThat(contentPost.getAdId()).isEqualTo("1,2,3");
        assertThat(contentPost.getName()).isEqualTo("데이먼스 이어의 플레이리스트");
        assertThat(contentPost.getTotalViews()).isEqualTo(0L);
        assertThat(contentPost.getTotalAdViews()).isEqualTo(0L);
        assertThat(contentPost.getTotalPlayTime()).isEqualTo(0L);
        assertThat(contentPost.isPublic()).isEqualTo(true);
        assertThat(contentPost.isDeleted()).isEqualTo(false);
        assertThat(contentPost.getDeletedAt()).isNull();
    }
}
