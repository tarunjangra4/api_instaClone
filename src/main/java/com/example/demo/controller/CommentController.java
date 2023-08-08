package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Comment;
import com.example.demo.modal.User;
import com.example.demo.response.MessageResponse;
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
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment, @PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException, CommentException {
        User user  = userService.findUserProfile(token);
        System.out.println("called");
        Comment createdComment = commentService.createComment(comment, postId, user);
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<CommentDto> getComment(){
//
//    }

    @PostMapping("/like/{commentId}/{postId}")
    public ResponseEntity<Comment> likeCommentHandler(@PathVariable Integer commentId, @PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user = userService.findUserProfile(token);

        Comment comment  = commentService.likeComment(commentId, postId, user.getId());
//        Comment comment = new Comment();s
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
//
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

    @PutMapping("/delete/{commentId}")
    public ResponseEntity<MessageResponse> deleteCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user = userService.findUserProfile(token);
        String message  = commentService.deleteComment(commentId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }
}
