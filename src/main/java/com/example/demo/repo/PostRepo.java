package com.example.demo.repo;

import com.example.demo.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT p from Post p where p.user.id = ?1")
    List<Post> findPostByUserId(@Param("userId") Integer userId);

//    List<Post> findAllByUserId(Integer userId);
    
    @Query(value="SELECT p from Post p where p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findAllPostsByUserIds(@Param("userIds") List<Integer> userIds);

//    List<Post> findAllByUserIdIn(Collection<Integer> userId);

//    @Query("SELECT p from post p where p.user.id IN : users")
//    public List<Post> findAllPostsByUserIds(@Param("users") List<Integer> userIds);
}
