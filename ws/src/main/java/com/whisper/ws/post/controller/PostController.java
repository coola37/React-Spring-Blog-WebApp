package com.whisper.ws.post.controller;

import com.whisper.ws.post.entity.Comment;
import com.whisper.ws.post.entity.Post;
import com.whisper.ws.post.service.CommentService;
import com.whisper.ws.post.service.PostService;
import com.whisper.ws.user.configuration.CurrentUser;
import com.whisper.ws.user.response.InternalApiResponse;
import com.whisper.ws.user.service.UserService;
import com.whisper.ws.user.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.whisper.ws.user.repository.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @PostMapping("/comment/send")
    ResponseEntity<?> sendComment(@RequestBody Comment comment, @AuthenticationPrincipal CurrentUser currentUser){
        User senderUser = userService.getUser(currentUser.getUserId());

        Comment response = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .postId(comment.getPostId())
                .senderId(currentUser.getUserId())
                .body(comment.getBody())
                .senderProfileImg(senderUser.getImage())
                .senderUsername(senderUser.getUsername())
                .likedById(new ArrayList<>())
                .likeCount(0)
                .build();
        commentService.postComment(response);
        InternalApiResponse<Object> internalApiResponse = InternalApiResponse.builder()
                .message(new GenericMessage("Comment has been sent"))
                .hasError(false)
                .payload(response)
                .httpStatus(OK)
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @PostMapping("/posts")
    ResponseEntity<?> sendPost(@RequestBody Post post, @AuthenticationPrincipal CurrentUser currentUser){
        Post response = Post.builder()
                .postId(UUID.randomUUID().toString())
                .senderId(currentUser.getUserId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
        postService.sendPost(response);
        InternalApiResponse<Object> internalApiResponse = InternalApiResponse.builder()
                .message(new GenericMessage("The post has been sent"))
                .hasError(false)
                .payload(response)
                .httpStatus(OK)
                .build();

        return ResponseEntity.ok(internalApiResponse);
    }


    @GetMapping("/posts/get/{postId}")
    ResponseEntity<?> getPostById(@PathVariable String postId){
        Post response = postService.getPost(postId);
        InternalApiResponse<Object> internalApiResponse = InternalApiResponse.builder()
                .httpStatus(OK)
                .payload(response)
                .hasError(false)
                .message(new GenericMessage("Fetched post"))
                .build();
        return ResponseEntity.ok(internalApiResponse);
    }

    @GetMapping("/posts")
    Page<Post> getPosts(Pageable pageable){
        return postService.getPosts(pageable);
    }

    @GetMapping("/comments/{postId}")
    Page<Comment> getCommentsByPostId(@PathVariable String postId, @AuthenticationPrincipal CurrentUser currentUser, Pageable pageable){
        return commentService.getCommentsByPostId(pageable, postId);
    }

    @DeleteMapping("/posts/{postId}")
    ResponseEntity<?> deletePost(@PathVariable String postId){
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @PutMapping("comment/like/{commentId}")
    ResponseEntity<?> likeOrDislike(@PathVariable String commentId,  @AuthenticationPrincipal CurrentUser currentUser){
        Comment comment = commentService.getComment(commentId);
        if(comment.getLikedById().contains(currentUser.getUserId())){
            List<Long> likedList = comment.getLikedById();
            likedList.remove(currentUser.getUserId());
            comment.setLikedById(likedList);
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentService.postComment(comment);
            return ResponseEntity.ok(new GenericMessage("Dislike"+ "likeCount:" + comment.getLikeCount()));
        }else{
            List<Long> likedList = comment.getLikedById();
            likedList.add(currentUser.getUserId());
            comment.setLikeCount(comment.getLikeCount() + 1);
            comment.setLikedById(likedList);
            commentService.postComment(comment);
            return ResponseEntity.ok(new GenericMessage("Like" + "likeCount:" + comment.getLikeCount() ));
        }
    }

}
