package com.whisper.ws.cacheservice.service;

import com.whisper.ws.cacheservice.feign.WsFeignClient;
import com.whisper.ws.cacheservice.repository.PostRepository;
import com.whisper.ws.cacheservice.repository.entity.Post;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository repository;
    private final WsFeignClient wsFeignClient;

    @Override
    public Post getPost(String postId) {
        Post post;
        try {
            Optional<Post> optionalPost = repository.findById(postId);
            if(optionalPost.isPresent()){
                post = optionalPost.get();
            }else{
                post = new Post();
                Post response = wsFeignClient.getPostById(postId).getBody().getPayload();
                post.setPostId(response.getPostId());
                post.setBody(response.getBody());
                post.setTitle(response.getTitle());
                post.setSenderId(response.getSenderId());
            }
        }catch (FeignException.FeignClientException.NotFound exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found for post id: "
            + postId);
        }
        return post;
    }

}
