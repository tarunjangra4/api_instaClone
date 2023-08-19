package com.example.demo.controller;

import com.example.demo.dto.StoryDto;
import com.example.demo.exceptions.StoryException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Story;
import com.example.demo.modal.User;
import com.example.demo.service.StoryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.creteStory(story, user.getId());
        return new ResponseEntity<>(createdStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> getStoriesByUserIdHandler(@PathVariable Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(user.getId());
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    @GetMapping("/all/{userIds}")
    public ResponseEntity<List<StoryDto>> getAllStoriesByUserIdsHandler(@PathVariable List<Integer> userIds, @RequestHeader("Authorization") String token) throws StoryException, UserException {
        User user = userService.findUserProfile(token);
        userIds.add(user.getId());
        List<StoryDto> stories = storyService.findStoriesByUserIds(userIds);
        System.out.println("stories "+stories);
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
}
