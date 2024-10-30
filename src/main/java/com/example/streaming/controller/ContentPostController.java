package com.example.streaming.controller;

import com.example.streaming.controller.port.ContentPostService;
import com.example.streaming.controller.request.ContentPostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentPostController {

    private final ContentPostService contentPostService;

    @PostMapping("/contents/")
    public void create(@RequestBody ContentPostCreate request) {
        contentPostService.create(request);
    }
}
