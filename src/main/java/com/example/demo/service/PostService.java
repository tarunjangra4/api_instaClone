package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    public Post createPost(Post post, Integer userId) throws PostException, UserException;

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException, CommentException;

    public List<PostDto> findPostsByUserId(Integer userId) throws UserException;

    public PostDto findPostByPostId(Integer postId) throws PostException;

    public List<PostDto> getAllPosts(Integer userId) throws PostException, UserException;

    public String savePost(Integer postId, Integer userId) throws PostException, UserException;

    public String unsavePost(Integer postId, Integer userId) throws PostException, UserException;

    public String likePost(Integer postId, Integer userId) throws PostException, UserException;

    public String unlikePost(Integer postId, Integer userId) throws PostException, UserException;
    public List<PostDto> getSavedPosts(Integer userId) throws PostException;

}
