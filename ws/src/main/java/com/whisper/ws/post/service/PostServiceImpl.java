package com.whisper.ws.post.service;

import com.whisper.ws.post.repository.PostRepository;
import com.whisper.ws.post.entity.Post;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository repo;

    @Override
    public Post sendPost(Post post) {
        repo.save(post);
        return post;
    }

    @Override
    public Post getPost(String postId) {
        return repo.findByPostId(postId);
    }

    @Override
    public Page<Post> getPosts(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public GenericMessage deletePost(String postId) {
        repo.deleteById(postId);
        return new GenericMessage("Post has deleted");
    }
}
