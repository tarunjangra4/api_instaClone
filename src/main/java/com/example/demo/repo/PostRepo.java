package com.example.demo.repo;

import com.example.demo.dto.PostDto;
import com.example.demo.modal.Post;
import com.example.demo.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query("SELECT new com.example.demo.dto.PostDto(p.id, p.image, p.caption, p.location, p.createdAt, u.id, u.name, u.username, u.userImage) " +
            "FROM Post p JOIN User u ON p.user.id=u.id WHERE p.user.id=:userId")
    List<PostDto> findPostByUserId(@Param("userId") Integer userId);

//    List<Post> findAllByUserId(Integer userId);

    @Query("SELECT new com.example.demo.dto.PostDto(p.id, p.image, p.caption, p.location, p.createdAt, u.id, u.name, u.username, u.userImage) " +
            "FROM Post p JOIN User u ON p.user.id=u.id WHERE p.id=:postId")
    PostDto findPostBypostId(@Param("postId") Integer postId);

//    @Query(value="SELECT p from Post p where p.user.id IN :userIds ORDER BY p.createdAt DESC")
    @Query("SELECT new com.example.demo.dto.PostDto(p.id, p.image, p.caption, p.location, p.createdAt, u.id, u.name, u.username, u.userImage) " +
            "FROM Post p JOIN User u ON p.user.id=u.id WHERE p.user.id IN (:userIds)")
    List<PostDto> findAllPostsByUserIds(@Param("userIds") List<Integer> userIds);

//    @Query("SELECT new com.example.demo.dto.PostDto(p.id, p.image, p.caption, p.location, p.createdAt, " +
//            "(SELECT new com.example.demo.dto.IdAndNameDto(u.id, u.name) " +
//            "FROM User u " +
//            "JOIN u.likedPosts lp " +
//            "WHERE lp.id = p.id) " +
//            ") FROM Post p " +
//            "WHERE p.user.id = 2")
//    List<PostDto> findAllPostsByUserIds(@Param("userIds") List<Integer> userIds);

    //    @Query(value = "SELECT DISTINCT u FROM User u JOIN p.likedByUsers WHERE lp.user.id IN :userIds")
//    @Query(value = "SELECT p.likedByUsers from Post p where p.user.id IN :userIds")
//    List<User> findLikedByUser(@Param("userIds") List<Integer> userIds);

//    List<Post> findAllByUserIdIn(Collection<Integer> userId);

//    @Query("SELECT p from post p where p.user.id IN : users")
//    public List<Post> findAllPostsByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT new com.example.demo.dto.PostDto(p.id, p.image, p.caption, p.location, p.createdAt, " +
            "u.id, u.name, u.username, u.userImage) " +
            "FROM Post p " +
            "JOIN User u ON p.user.id = u.id " +
            "JOIN SavedPosts sp ON sp.postId=p.id " +
            "WHERE sp.userId=:userId")
    List<PostDto> getSavedPosts(@Param("userId") Integer userId);
}
