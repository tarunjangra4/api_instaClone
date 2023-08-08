package com.example.demo.repo;

import com.example.demo.dto.IdAndNameDto;
import com.example.demo.modal.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT c FROM Comment c JOIN Post p ON c.post.id = p.id WHERE p.id = :postId")
    List<Comment> findCommentsByPostId(@Param("postId") Integer postId);

    @Query(value = "SELECT new com.example.demo.dto.IdAndNameDto(u.id, u.name) FROM User u LEFT JOIN Comment c ON u.id=c.user.id WHERE c.id=:commentId")
    List<IdAndNameDto> likedByUser(@Param("commentId") Integer commentId);

    @Query("SELECT c.id FROM Comment c JOIN Post p ON c.post.id = p.id WHERE p.id = :postId")
    List<Integer> getCommentIdsByPostId(@Param("postId") Integer postId);

//    void deleteAllCommentsByCommentIds(@Param("commentIds") )

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id=:commentId")
    void deleteSingleCommentByCommentId(@Param("commentId") Integer commentId);
}
