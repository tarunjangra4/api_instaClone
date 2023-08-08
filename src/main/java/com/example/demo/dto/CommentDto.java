package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {

    private Integer commentId;
    private String content;
    private LocalDateTime createdAt;
    private IdAndNameDto createdBy;
//    private Integer postId;
    private List<IdAndNameDto> likedBy;
}
