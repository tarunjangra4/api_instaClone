package com.example.demo.repo;

import com.example.demo.dto.IdAndNameDto;
import com.example.demo.modal.PostLikeBy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikesRepo extends JpaRepository<PostLikeBy, Integer> {

    @Query(value = "SELECT new com.example.demo.dto.IdAndNameDto(u.id, u.name) " +
            "FROM PostLikeBy pl JOIN User u ON u.id = pl.userId " +
            "where pl.postId = :postId")
    List<IdAndNameDto> getLikedByUserIds(@Param("postId") Integer postId);

    @Query("SELECT pl.id from PostLikeBy pl WHERE pl.postId=:postId AND pl.userId=:userId")
    Integer getIdsOfPosts(@Param("postId") Integer postId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PostLikeBy p WHERE p.id IN :id ")
    void unlikePost(@Param("id") Integer id);
}
