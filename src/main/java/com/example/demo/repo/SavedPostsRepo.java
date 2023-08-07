package com.example.demo.repo;

import com.example.demo.modal.SavedPosts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedPostsRepo extends JpaRepository<SavedPosts, Integer> {

    @Query("SELECT CASE WHEN COUNT(sp)>0 THEN true ELSE false END FROM SavedPosts sp WHERE sp.postId=:postId AND sp.userId=:userId")
    public boolean isPostSavedByUser(@Param("userId") Integer userId, @Param("postId") Integer postId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SavedPosts sp WHERE sp.postId=:postId AND sp.userId=:userId")
    public void unsavePost(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
