package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class StoryDto {
    private String image;
    private LocalDateTime time;
    private Integer userId;
}
