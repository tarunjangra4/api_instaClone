package com.example.demo.repo;

import com.example.demo.dto.IdAndNameDto;
import com.example.demo.modal.CommentLikes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikesRepo extends JpaRepository<CommentLikes, Integer> {

    @Query("SELECT cl.userId FROM CommentLikes cl WHERE cl.commentId=:commentId")
    List<Integer> getLikedByUserIds(@Param("commentId") Integer commentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentLikes cl WHERE cl.commentId = :commentId AND cl.userId=:userId ")
    void unlikeComment(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentLikes cl WHERE cl.commentId IN (:commentIds)")
    void deleteCommentsLikeByCommentIds(@Param("commentIds") List<Integer> commentIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentLikes cl WHERE cl.commentId=:commentId")
    void deleteCommentsLikeByCommentId(@Param("commentId") Integer commentId);

}
