package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.exceptions.CommentException;
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
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());
        System.out.println("post created "+createdPost);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/curr-user-posts")
    public ResponseEntity<List<PostDto>> getLoggedInUserPosts(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        List<PostDto> posts = postService.findPostsByUserId(user.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<PostDto>> getAllPostHandler(@RequestHeader("Authorization") String token) throws PostException, UserException {
        Integer userId = userService.findUserProfile(token).getId();
        List<PostDto> posts = postService.getAllPosts(userId);
//        System.out.println("posts "+posts.size());
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/singlePost/{postId}")
    public ResponseEntity<PostDto> findPostbyIdHandler(@PathVariable Integer postId) throws PostException {
        PostDto post = postService.findPostByPostId(postId);
        System.out.println("post "+post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public  ResponseEntity<MessageResponse> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);

        String message = postService.likePost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public  ResponseEntity<MessageResponse> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);

        String message = postService.unlikePost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{postId}")
    @PutMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException, CommentException {
        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException  {
        User user = userService.findUserProfile(token);
        String message = postService.savePost(postId, user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unsavePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        String message = postService.unsavePost(postId,user.getId());
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("/saved_posts")
    public ResponseEntity<List<PostDto>> getSavedPostsHandler(@RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);
        List<PostDto> posts = postService.getSavedPosts(user.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsernameHandler(@PathVariable String username) throws UserException {
        User user = userService.findUserByUsername(username);
        List<PostDto> posts = postService.findPostsByUserId(user.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/saved_posts/{username}")
    public ResponseEntity<List<PostDto>> getSavedPostsByUsernameHandler(@PathVariable String username) throws UserException, PostException {
        System.out.println("saved posts");
        User user = userService.findUserByUsername(username);
        List<PostDto> posts = postService.getSavedPosts(user.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
