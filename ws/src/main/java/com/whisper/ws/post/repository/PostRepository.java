package com.whisper.ws.post.repository;

import com.whisper.ws.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    public Post findByPostId(String postId);
}
