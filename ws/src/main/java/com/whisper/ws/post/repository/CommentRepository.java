package com.whisper.ws.post.repository;

import com.whisper.ws.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    public Page<Comment> getCommentsByPostId(Pageable pageable, String postId);
    public Comment getCommentByCommentId(String commentId);
}
