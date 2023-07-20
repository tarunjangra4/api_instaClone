package com.example.demo.controller;

import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Comment;
import com.example.demo.modal.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment, @PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user  = userService.findUserProfile(token);

        Comment createdComment = commentService.createComment(comment, postId, user.getId());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user = userService.findUserProfile(token);

        Comment comment  = commentService.likeComment(commentId, user.getId());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comment> unlikeCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user = userService.findUserProfile(token);

        Comment comment  = commentService.unlikeComment(commentId, user.getId());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

//    @GetMapping("/comments/{postId}")
//    public  ResponseEntity<Comment> getCommentsByPostId(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException {
//        User user = userService.findUserProfile(token);
//
//        Comment comment  = commentService.unlikeComment(commentId, user.getId());
//        return new ResponseEntity<>(comment, HttpStatus.OK);
//    }
}
