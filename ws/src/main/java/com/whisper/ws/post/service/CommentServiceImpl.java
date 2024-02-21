package com.whisper.ws.post.service;

import com.whisper.ws.post.repository.CommentRepository;
import com.whisper.ws.post.entity.Comment;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository repo;
    @Override
    public Page<Comment> getCommentsByPostId(Pageable pageable, String postId) {
        return repo.getCommentsByPostId(pageable,postId);
    }

    @Override
    public GenericMessage deleteComment(String commentId) {
        repo.deleteById(commentId);
        return new GenericMessage("Comment has been deleted");
    }

    @Override
    public Comment postComment(Comment comment) {
        return repo.save(comment);
    }

    @Override
    public Comment getComment(String commentId) {
        return repo.getCommentByCommentId(commentId);
    }
}
