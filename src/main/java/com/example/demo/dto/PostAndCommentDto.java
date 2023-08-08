package com.example.demo.dto;

import com.example.demo.modal.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostAndCommentDto {
    private Integer postId;
//    private String username;
//    private String name;
//    private String email;
//    private String userImage;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    List<Comment> comments;
}
