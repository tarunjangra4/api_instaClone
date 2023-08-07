package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Comment;
import com.example.demo.modal.User;
import com.example.demo.response.MessageResponse;

import java.util.List;

public interface CommentService {

    public Comment createComment(Comment comment, Integer postId, User user) throws UserException, PostException, CommentException;

    public Comment findCommentById(Integer commentId) throws CommentException;

    public List<CommentDto> getCommentsByPostId(Integer postId);

    public List<Comment> findAllCommentsByPostId(Integer postId);

    public Comment likeComment(Integer commentId, Integer postId, Integer userId) throws CommentException, UserException;

    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException;

    public String deleteComment(Integer commentId, Integer userId) throws CommentException, UserException;
}
