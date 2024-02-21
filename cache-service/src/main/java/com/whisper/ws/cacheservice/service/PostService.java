package com.whisper.ws.cacheservice.service;

import com.whisper.ws.cacheservice.repository.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post getPost(String postId);
}
