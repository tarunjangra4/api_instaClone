package com.example.demo.controller;

import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token) throws UserException, PostException {
        System.out.println("creationg started");
        User user = userService.findUserProfile(token);
        System.out.println("user Created");
        Post createdPost = postService.createPost(post, user.getId());
        System.out.println("post created");
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/allPosts/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable Integer userId) throws UserException {

//        List<Post> posts = postService.findPostsByUserId(userId);
        List<Post> posts = new ArrayList<>();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{userIds}")
    public ResponseEntity<List<Post>> findAllPostByUserIdsHandler(@PathVariable List<Integer> userIds) throws PostException, UserException {
        System.out.println("ids list "+userIds);
        List<Post> posts = postService.findAllPostsByUserIds(userIds);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostbyIdHandler(@PathVariable Integer postId) throws PostException {
        Post post = postService.findPostByPostId(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public  ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);

        Post likedPost = postService.likePost(postId, user.getId());
        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public  ResponseEntity<Post> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);

        Post unlikedPost = postService.unlikePost(postId, user.getId());
        return new ResponseEntity<>(unlikedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<String> savePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException  {
        User user = userService.findUserProfile(token);
        String message = postService.savePost(postId, user.getId());
//        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<String> unsavePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.unsavePost(postId,user.getId());
//        MessageResponse res = new MessageResponse(message);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}
