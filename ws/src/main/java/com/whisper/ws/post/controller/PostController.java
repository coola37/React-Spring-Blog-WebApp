package com.whisper.ws.post.controller;

import com.whisper.ws.post.repository.entity.Post;
import com.whisper.ws.post.service.PostService;
import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.response.InternalApiResponse;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/save")
    ResponseEntity<?> sendPost(@RequestBody Post post, @AuthenticationPrincipal CurrentUser currentUser){
        Post response = Post.builder()
                .postId(UUID.randomUUID().toString())
                .senderId(currentUser.getUserId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
        postService.sendPost(response);
        InternalApiResponse internalApiResponse = InternalApiResponse.builder()
                .message(new GenericMessage("The post has been sent"))
                .hasError(false)
                .payload(response)
                .httpStatus(OK)
                .build();

        return ResponseEntity.ok(internalApiResponse);
    }

    @GetMapping("/get/{postId}")
    ResponseEntity<?> getPostById(@PathVariable String postId){
        Post response = postService.getPost(postId);
        InternalApiResponse internalApiResponse = InternalApiResponse.builder()
                .httpStatus(OK)
                .payload(response)
                .hasError(false)
                .message(new GenericMessage("Fetched post"))
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @GetMapping
    Page<Post> getPosts(Pageable pageable){
        return postService.getPosts(pageable);
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<?> deletePost(@PathVariable String postId){
        return ResponseEntity.ok(postService.deletePost(postId));
    }



}
