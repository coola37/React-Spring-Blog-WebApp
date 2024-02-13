package com.whisper.ws.post.repository.entity;

import jakarta.persistence.*;
import com.whisper.ws.user.repository.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    String postId;
    //@ManyToOne
    long senderId;
    String title;
    String body;
}