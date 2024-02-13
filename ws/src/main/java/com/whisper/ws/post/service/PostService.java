package com.whisper.ws.post.service;

import com.whisper.ws.post.repository.entity.Post;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    public Post sendPost(Post post);

    public Post getPost(String postId);
    public Page<Post> getPosts(Pageable pageable);
    public GenericMessage deletePost(String postId);
}
