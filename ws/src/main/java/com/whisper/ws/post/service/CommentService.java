package com.whisper.ws.post.service;

import com.whisper.ws.post.entity.Comment;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    public Page<Comment> getCommentsByPostId(Pageable pageable, String postId);

    //public Page<Post> getPosts(Pageable pageable);
    public GenericMessage deleteComment(String commentId);
    public Comment postComment(Comment comment);
    public Comment getComment(String commentId);
}
