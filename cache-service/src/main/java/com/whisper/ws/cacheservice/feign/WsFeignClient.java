package com.whisper.ws.cacheservice.feign;

import com.whisper.ws.cacheservice.repository.entity.Post;
import com.whisper.ws.cacheservice.response.InternalApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${feign.whisper-web-service.name}")
public interface WsFeignClient {

    @GetMapping(value = "/api/v1/posts/get/{postId}")
    ResponseEntity<InternalApiResponse<Post>> getPostById(@PathVariable String postId);


}
