package com.example.demo.repo;

import com.example.demo.dto.UserDto;
import com.example.demo.modal.Follows;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface FollowsRepo extends JpaRepository<Follows, Integer> {

    @Query(value = "SELECT f.following.id from Follows f where f.follower.id=:userId")
    public List<Integer> findAllFollowingUsers(@Param("userId") Integer userId);

    @Query(value = "SELECT f.following.id from Follows f where f.follower.id=:userId")
    public List<Integer> findFollowingUsersByUserId(@Param("userId") Integer userId);

    @Query("SELECT f.follower.id from Follows f where f.following.id=:userId")
    public List<Integer> getFollowersIdByUserId(@Param("userId") Integer userId);

    @Query("SELECT DISTINCT f.following.id FROM Follows f WHERE f.follower.id!=:userId ORDER BY RAND()")
    public List<Integer> getSuggestedUserList(@Param("userId") Integer userId);

//    @Query("SELECT CASE WHEN f.follower.id IS NOT Null AND f.following.id=:followingId THEN true ELSE false END FROM Follows f WHERE f.follower.id=:followerId")
//    public boolean isFollower(@Param("followerId") Integer followerId, @Param("followingId") Integer followingId);

    Boolean existsByFollowerIdAndFollowingId(Integer follower, Integer followingId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Follows f WHERE f.follower.id=:loggedInUserId AND f.following.id=:unfollowedUserId")
    void unfollowUser(@Param("loggedInUserId") Integer loggedInUserId, @Param("unfollowedUserId") Integer unfollowedUserId);
}
