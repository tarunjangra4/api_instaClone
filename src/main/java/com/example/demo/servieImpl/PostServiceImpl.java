package com.example.demo.servieImpl;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.IdAndNameDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.*;
import com.example.demo.repo.*;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostLikesRepo postLikesRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FollowsRepo followsRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikesRepo commentLikesRepo;

    @Autowired
    private SavedPostsRepo savedPostsRepo;

    @Override
    public Post createPost(Post post, Integer userId) throws PostException, UserException {
        User user =  userService.findUserById(userId);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepo.save(post);
        return post;
    }

    @Override
    @Transactional
//    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException {
    public List<PostDto> getAllPosts(Integer userId) throws PostException, UserException {
        List<Integer> followingUserIds = followsRepo.findAllFollowingUsers(userId);
        followingUserIds.add(userId);

        List<PostDto> posts = postRepo.findAllPostsByUserIds(followingUserIds);
        List<PostDto> allPosts = new ArrayList<>();

        for(PostDto postDto : posts){
            System.out.println("post "+postDto);
            List<IdAndNameDto> likedByUsers = postLikesRepo.getLikedByUserIds(postDto.getPostId());
            postDto.setLikeBy(likedByUsers);
            List<CommentDto> comments = commentService.getCommentsByPostId(postDto.getPostId());
            postDto.setComments(comments);
            postDto.setIsPostSaved(savedPostsRepo.isPostSavedByUser(userId, postDto.getPostId()));
            allPosts.add(postDto);
        }
        if(posts.size()==0){
            throw new PostException("No post available");
        }
        return allPosts;
    }

    @Override
    public List<PostDto> findPostsByUserId(Integer userId) throws UserException {
        List<PostDto> posts = postRepo.findPostByUserId(userId);
        for(PostDto postDto : posts){
            System.out.println("post "+postDto);
            List<IdAndNameDto> likedByUsers = postLikesRepo.getLikedByUserIds(postDto.getPostId());
            postDto.setLikeBy(likedByUsers);
            List<CommentDto> comments = commentService.getCommentsByPostId(postDto.getPostId());
            postDto.setComments(comments);
            postDto.setIsPostSaved(savedPostsRepo.isPostSavedByUser(userId, postDto.getPostId()));
        }
        if(posts.size()==0){
            throw new UserException("this user does not have any post");
        }
        return posts;
    }

    @Override
    public PostDto findPostByPostId(Integer postId) throws PostException {
        Optional<Post> opt = postRepo.findById(postId);
        PostDto post = new PostDto();
        if(opt.isPresent()){
            Post p = opt.get();
            post.setPostId(p.getId());
            post.setCaption(p.getCaption());
            post.setLocation(p.getLocation());
            post.setImage(p.getImage());
            post.setCreatedAt(p.getCreatedAt());
            List<IdAndNameDto> likedByUsers = postLikesRepo.getLikedByUserIds(p.getId());
            post.setLikeBy(likedByUsers);
            System.out.println("liked by "+ post.getLikeBy());
            User u = p.getUser();
            post.setCreatedBy(new UserDto(u.getId(), u.getName(), u.getUsername(), u.getUserImage()));
            List<CommentDto> comments = commentService.getCommentsByPostId(postId);
            post.setComments(comments);
            System.out.println("start "+post);
        }
        System.out.println("end "+post);
        return post;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException, CommentException {
        PostDto post = postRepo.findPostBypostId(postId);
        List<Integer> commentIds = commentRepo.getCommentIdsByPostId(postId);
        // delete comments
        for(int commentId : commentIds){
            commentService.deleteComment(commentId, userId);
        }
        // delete Post
        if(post.getCreatedBy().getId().equals(userId)){
            postRepo.deleteById(postId);
        }
        throw  new PostException("You can't delete other users post");
    }

    @Override
    public String savePost(Integer postId, Integer userId) throws PostException, UserException {
        try{
            SavedPosts savedPosts = new SavedPosts(postId,userId);
            savedPostsRepo.save(savedPosts);
            return "Post Saved Successfully";
        }catch (Exception e){
            throw new PostException(e.getMessage());
        }

    }

    @Override
    public String unsavePost(Integer postId, Integer userId) throws PostException, UserException {
        try{
            savedPostsRepo.unsavePost(userId, postId);
            return "Post Unsaved Successfully";
        }catch (Exception e){
            throw new PostException(e.getMessage());
        }
    }

    @Override
    public String likePost(Integer postId, Integer userId) throws PostException, UserException {
        Integer id = postLikesRepo.getIdsOfPosts(postId, userId);
        if(id!=null){
            throw new PostException("Post is already Liked");
        }
        PostLikeBy postLikeBy = new PostLikeBy(postId, userId);
        postLikesRepo.save(postLikeBy);
        return "Post Liked Successfully";
    }

    @Override
    public String unlikePost(Integer postId, Integer userId) throws PostException, UserException {
        Integer id = postLikesRepo.getIdsOfPosts(postId, userId);
        postLikesRepo.unlikePost(id);
//        return postRepo.save();
        return "Post unliked successfully";
    }

    @Override
    public List<PostDto> getSavedPosts(Integer userId) throws PostException {
//        List<Integer> followingUserIds = followsRepo.findAllFollowingUsers(userId);
//        followingUserIds.add(userId);
//
//        List<PostDto> posts = postRepo.findAllPostsByUserIds(followingUserIds);
//        List<PostDto> allPosts = new ArrayList<>();
//
//        for(PostDto postDto : posts){
//            System.out.println("post "+postDto);
//            List<IdAndNameDto> likedByUsers = postLikesRepo.getLikedByUserIds(postDto.getPostId());
//            postDto.setLikeBy(likedByUsers);
//            List<CommentDto> comments = commentService.getCommentsByPostId(postDto.getPostId());
//            postDto.setComments(comments);
//            postDto.setIsPostSaved(savedPostsRepo.isPostSavedByUser(userId, postDto.getPostId()));
//            allPosts.add(postDto);
//        }
        List<PostDto> posts = postRepo.getSavedPosts(userId);
        for(PostDto postDto : posts){
            System.out.println("post "+postDto);
            List<IdAndNameDto> likedByUsers = postLikesRepo.getLikedByUserIds(postDto.getPostId());
            postDto.setLikeBy(likedByUsers);
            List<CommentDto> comments = commentService.getCommentsByPostId(postDto.getPostId());
            postDto.setComments(comments);
            postDto.setIsPostSaved(savedPostsRepo.isPostSavedByUser(userId, postDto.getPostId()));
        }
        return posts;
    }
}
