package com.example.demo.service;

import com.example.demo.exceptions.StoryException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Story;

import java.util.List;

public interface StoryService {

    public Story creteStory(Story story, Integer userId) throws UserException;

    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}
