package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.IdAndNameDto;
import com.example.demo.dto.SuggestionUserDto;
import com.example.demo.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modal.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT u From User u Where u.id IN :users")
	public List<User> findAllUsersByUserIds(@Param("users") List<Integer> userIds);
	
	@Query("SELECT DISTINCT u From User u Where u.username LIKE %:query% OR u.email LIKE %:query%")
	public List<User> findByQuery(@Param("query") String query);

	@Query("SELECT new com.example.demo.dto.IdAndNameDto(u.id,u.name) FROM User u Where u.id IN :userIds")
	public List<IdAndNameDto> findUsersByUserIds(@Param("userIds") List<Integer> userIds);

	@Query("SELECT new com.example.demo.dto.IdAndNameDto(u.id,u.name) FROM User u WHERE u.id=:userId")
	public IdAndNameDto getUserIdAndName(@Param("userId") Integer userId);

//	@Query("SELECT new com.example.demo.dto.SuggestionUserDto(u.id, u.name, u.username, CASE WHEN f.following.id=:loggedInUserId THEN true ELSE false END) FROM User u JOIN Follows f ON u.id=f.follower.id WHERE u.id=:userId")
//	public SuggestionUserDto getSuggestedUserDetails(@Param("userId") Integer userId, @Param("loggedInUserId") Integer loggedInUser);

	@Query("SELECT new com.example.demo.dto.SuggestionUserDto(u.id, u.name, u.username) FROM User u WHERE NOT u.id IN (:userId)")
	public List<SuggestionUserDto> getSuggestedUserDetails(@Param("userId") List<Integer> userId);

	@Query("SELECT new com.example.demo.dto.UserDto(u.id, u.name, u.username, u.userImage, u.email) FROM User u JOIN Follows f ON f.follower.id=u.id WHERE f.following.id=:userId ")
	public List<UserDto> getFollowersList(@Param("userId") Integer userId);

	@Query("SELECT new com.example.demo.dto.UserDto(u.id, u.name, u.username, u.userImage, u.email) FROM User u JOIN Follows f ON f.following.id=u.id WHERE f.follower.id=:userId ")
	public List<UserDto> getFollowingUsersList(@Param("userId") Integer userId);
}
