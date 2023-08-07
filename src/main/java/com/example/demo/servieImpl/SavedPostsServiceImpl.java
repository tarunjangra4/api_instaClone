package com.example.demo.servieImpl;

import com.example.demo.repo.SavedPostsRepo;
import com.example.demo.service.SavedPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavedPostsServiceImpl implements SavedPostsService {

    @Autowired
    private SavedPostsRepo savedPostsRepo;

    @Override
    public boolean isPostSavedByUser(Integer postId, Integer userId) {
        return savedPostsRepo.isPostSavedByUser(userId, postId);
    }
}
