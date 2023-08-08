package com.example.demo.servieImpl;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.IdAndNameDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.PostException;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.*;
import com.example.demo.repo.CommentLikesRepo;
import com.example.demo.repo.CommentRepo;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserService userService;
//
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentLikesRepo commentLikesRepo;

    @Override
    public Comment createComment(Comment comment, Integer postId, User user) throws UserException, PostException, CommentException {
//        Post post = postService.findPostByPostId(postId);

//        UserDto userDto = new UserDto();
//        userDto.setEmail(user.getEmail());
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setUserImage(user.getUserImage());
//        userDto.setUsername(user.getUsername());

//        comment.setUser(userDto);
        Comment createdComment = new Comment();
        createdComment.setContent(comment.getContent());
        createdComment.setCreatedAt(LocalDateTime.now());
        createdComment.setUser(user);
        Optional<Post> opt = postRepo.findById(postId);
        if(opt.isPresent()){
            Post post = opt.get();
            createdComment.setPost(post);
            commentRepo.save(createdComment);
            return createdComment;
        }
        throw new CommentException("Comment cannot be created");
//        return null;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> opt = commentRepo.findById(commentId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new CommentException("Comment not exist with id: "+commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Integer postId) {
        List<Comment> comments = commentRepo.findCommentsByPostId(postId);
        List<CommentDto> commentsDto = new ArrayList<>();
        for(Comment comment : comments){
            CommentDto commentDto = new CommentDto();
            commentDto.setCommentId(comment.getId());
            commentDto.setContent(comment.getContent());
            commentDto.setCreatedBy(new IdAndNameDto(comment.getUser().getId(),comment.getUser().getName()));
            commentDto.setCreatedAt(comment.getCreatedAt());
            List<Integer> likedByUserIds = commentLikesRepo.getLikedByUserIds(comment.getId());
            List<IdAndNameDto> likedByUser = userRepo.findUsersByUserIds(likedByUserIds);
            commentDto.setLikedBy(likedByUser);
            commentsDto.add(commentDto);
        }
        return commentsDto;
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Integer postId) {

        return null;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer postId, Integer userId) throws CommentException, UserException {
//        User user = userService.findUserById(userId);
//        Comment comment = findCommentById(commentId);
//
//        UserDto userDto = new UserDto();
//        userDto.setEmail(user.getEmail());
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setUserImage(user.getUserImage());
//        userDto.setUsername(user.getUsername());

        List<Integer> likedByUserIds = commentLikesRepo.getLikedByUserIds(commentId);
        if(likedByUserIds.contains(userId)){
            throw new CommentException("Comment is already Liked");
        }
        CommentLikes commentLikeBy = new CommentLikes(commentId, postId, userId);
        commentLikesRepo.save(commentLikeBy);
        return null;

//        comment.getLikedByUsers().add(userDto);
//        commentRepo.save(comment)
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
//        User user = userService.findUserById(userId);
//        Comment comment = findCommentById(commentId);
//
//        UserDto userDto = new UserDto();
//        userDto.setEmail(user.getEmail());
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setUserImage(user.getUserImage());
//        userDto.setUsername(user.getUsername());

//        comment.getLikedByUsers().remove(userDto);

//        return commentRepo.save(comment);
        commentLikesRepo.unlikeComment(commentId, userId);
        return null;
    }

    public String deleteComment(Integer commentId, Integer userId) throws CommentException{
        Optional<Comment> opt = commentRepo.findById(commentId);

        if(opt.isPresent()){
            Comment comment = opt.get();
            Integer createdUserId = comment.getUser().getId();
            // this will fetch the parent post of the comment and then get the user who created the post
            Integer postCreatorUserId = comment.getPost().getUser().getId();
            if(userId==createdUserId || userId==postCreatorUserId){
                // delete the comment likes details
                commentLikesRepo.deleteCommentsLikeByCommentId(commentId);
                // delete the comment
                commentRepo.deleteSingleCommentByCommentId(commentId);
                return "Comment has been deleted";
            }else{
                throw new CommentException("You cannot delete someone else comment");
            }
        }else{
            throw new CommentException("Comment is not Present");
        }
    }
}
