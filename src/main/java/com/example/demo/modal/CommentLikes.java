package com.example.demo.modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_likes")
@Data
@NoArgsConstructor
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "user_id")
    private Integer userId;

    public CommentLikes(Integer commentId, Integer postId, Integer userId) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
    }
}
