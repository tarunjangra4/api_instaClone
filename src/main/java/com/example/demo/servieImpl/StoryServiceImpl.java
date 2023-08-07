package com.example.demo.servieImpl;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.StoryException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Story;
import com.example.demo.modal.User;
import com.example.demo.repo.StoryRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.StoryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepo storyRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Story creteStory(Story story, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        UserDto userDto  = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getUserImage());
        userDto.setUsername(user.getUsername());

//        story.setUser(userDto);
        story.setUser(user);
        story.setTimeStamp(LocalDateTime.now());

//        user.getStories().add(story);

        return storyRepo.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
//        List<Story> stories = user.getStories();
        List<Story> stories = new ArrayList<>();
        if(stories.size()==0){
            throw new StoryException("this user does not have any story");
        }
        return stories;
    }
}
