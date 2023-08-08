package com.example.demo.modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saved_posts")
@Data
@NoArgsConstructor
public class SavedPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Integer postId;

    private Integer userId;

    public SavedPosts(Integer postId, Integer userId){
        this.postId = postId;
        this.userId = userId;
    }
}
