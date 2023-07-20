package com.example.demo.repo;

import com.example.demo.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

//    @Query(value = "SELECT c FROM Comment c JOIN c.post p WHERE p.postId = :postId")
//    List<Comment> getCommentsByPostId(@Param("postId") Integer postId);
}
