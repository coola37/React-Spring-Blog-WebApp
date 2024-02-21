package com.whisper.ws.cacheservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@RedisHash("Post")
public class Post {

    @Id
    String postId;
    long senderId;
    String title;
    String body;

}