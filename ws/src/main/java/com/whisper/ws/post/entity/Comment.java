package com.whisper.ws.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    String commentId;
    long senderId;
    String postId;
    String senderUsername;
    String senderProfileImg;
    String body;
    List<Long> likedById;
    int likeCount;
}
