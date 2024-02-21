package com.whisper.ws.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    String postId;
    long senderId;
    String title;
    String body;

}