package com.example.demo.dto;

import com.example.demo.modal.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String image;
    private String caption;
    private String location;
    private LocalDateTime createdAt;
    private UserDto createdBy;

    private List<IdAndNameDto> likeBy;

    private List<CommentDto> comments;

    private Boolean isPostSaved;

    public PostDto(Integer postId, String image, String caption, String location, LocalDateTime createdAt, Integer userId, String name, String username, String userImage){
        this.postId = postId;
        this.image = image;
        this.caption = caption;
        this.location = location;
        this.createdAt = createdAt;
        this.createdBy = new UserDto(userId, name, username,userImage);
    }

}
