package com.example.demo.servieImpl;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Post createPost(Post post, Integer userId) throws PostException, UserException {
        User user =  userService.findUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getUserImage());
        userDto.setUsername(user.getUsername());

        post.setUser(userDto);
        post.setCreatedAt(LocalDateTime.now());

        Post createdPost = postRepo.save(post);
        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        if(post.getUser().getId().equals(user.getId())){
            postRepo.deleteById(postId);
            return "Post Deleted successfully";
        }
        throw  new PostException("You can't delete other users post");
    }

    @Override
    public List<Post> findPostsByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepo.findPostByUserId(userId);

        if(posts.size()==0){
            throw new UserException("this user does not have any post");
        }

        return posts;
    }

    @Override
    public Post findPostByPostId(Integer postId) throws PostException {
        Optional<Post> opt = postRepo.findById(postId);
        if(opt.isPresent()){
            Post p = opt.get();
            System.out.println("post data0 "+p.getComments().get(0).getLikedByUsers());
            System.out.println("post data1 "+p.getComments().get(1).getLikedByUsers());
            System.out.println("post data2 "+p.getComments().get(2).getLikedByUsers());
            System.out.println("post data3 "+p.getComments().get(3).getLikedByUsers());
            return opt.get();
        }
        throw new PostException("Post not found with id "+postId);
    }

    @Override
    public List<Post> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepo.findAllPostsByUserIds(userIds);
        if(posts.size()==0){
            throw new PostException("No post available");
        }
        return posts;
    }

    @Override
    public String savePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostByPostId(postId);

        User user = userService.findUserById(userId);

        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post);
            userRepo.save(user);
        }
        return "Post Saved Successfully";
    }

    @Override
    public String unsavePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostByPostId(postId);

        User user = userService.findUserById(userId);

        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            userRepo.save(user);
        }
        return "Post Removed Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getUserImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(userDto);

        return postRepo.save(post);
    }

    @Override
    public Post unlikePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getUserImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().remove(userDto);

        return postRepo.save(post);
    }
}
