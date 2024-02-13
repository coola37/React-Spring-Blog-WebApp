package com.whisper.ws.post.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    String commentId;
    //@ManyToOne
    long senderId;
    //@ManyToOne
    String postId;
    String body;
    String senderUsername;
    String senderProfileImg;
    int like = 0;
}
