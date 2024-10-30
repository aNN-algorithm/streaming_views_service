package com.example.streaming.model;

import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.model.ContentPostCreate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContentPostTest {

    @Test
    public void ContentPost로_동영상_업로드_가능() {

        // given
        ContentPostCreate contentPostCreate = ContentPostCreate.builder()
                .name("데이먼스 이어의 플레이리스트")
                .adId("1,2,3")
                .isPublic(true)
                .build();

        // when
        ContentPost contentPost = ContentPost.from(contentPostCreate);

        // then
        assertThat(contentPost.getId()).isNull();
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
