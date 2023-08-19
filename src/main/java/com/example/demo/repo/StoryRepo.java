package com.example.demo.repo;

import com.example.demo.modal.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface StoryRepo extends JpaRepository<Story, Integer> {

    @Query("SELECT s from Story s where s.user.id = :userId")
    List<Story> findAllStoryByUserId(@Param("userId") Integer userId);

    @Query("SELECT s FROM Story s WHERE s.user.id IN (:userIds)")
    List<Story> findAllStoriesByUserIds(@Param("userIds") List<Integer> userIds);
}
