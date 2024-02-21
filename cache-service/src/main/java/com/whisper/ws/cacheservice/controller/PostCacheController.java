package com.whisper.ws.cacheservice.controller;

import com.whisper.ws.cacheservice.repository.entity.Post;
import com.whisper.ws.cacheservice.response.InternalApiResponse;
import com.whisper.ws.cacheservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/post-cache")
@RequiredArgsConstructor
public class PostCacheController {
    private final PostService postService;

    @GetMapping(value = "/get/{postId}")
    public InternalApiResponse<Post> getPost(@PathVariable String postId){
        Post post = postService.getPost(postId);
        return InternalApiResponse.<Post>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(post)
                .build();
    }
}
