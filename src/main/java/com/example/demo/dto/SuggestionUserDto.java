package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionUserDto {
    private Integer userId;
    private String name;
    private String username;
//    private Integer followingUserId;
    private Boolean isFollowing;

    public SuggestionUserDto(Integer userId, String name, String username) {
        this.userId = userId;
        this.name = name;
        this.username = username;
    }
}
