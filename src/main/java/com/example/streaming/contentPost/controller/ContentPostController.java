package com.example.streaming.contentPost.controller;

import com.example.streaming.contentPost.model.ContentPost;
import com.example.streaming.contentPost.service.ContentPostService;
import com.example.streaming.contentPost.model.ContentPostCreate;
import com.example.streaming.cumulativeContentStatistics.model.CumulativeContentStatistics;
import com.example.streaming.cumulativeContentStatistics.service.CumulativeContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContentPostController {

    private final ContentPostService contentPostService;
    private final CumulativeContentService cumulativeContentService;

    @PostMapping("/contents/")
    public void create(@RequestBody ContentPostCreate request) {
        ContentPost contentPost = contentPostService.create(request);
        cumulativeContentService.create(contentPost.getId());
    }
}
