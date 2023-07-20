package com.example.demo.service;

import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    public Post createPost(Post post, Integer userId) throws PostException, UserException;

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    public List<Post> findPostsByUserId(Integer userId) throws UserException;

    public Post findPostByPostId(Integer postId) throws PostException;

    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException;

    public String savePost(Integer postId, Integer userId) throws PostException, UserException;

    public String unsavePost(Integer postId, Integer userId) throws PostException, UserException;

    public Post likePost(Integer postId, Integer userId) throws PostException, UserException;

    public Post unlikePost(Integer postId, Integer userId) throws PostException, UserException;

}
