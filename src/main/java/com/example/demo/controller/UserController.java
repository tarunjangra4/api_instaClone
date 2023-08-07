package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.SuggestionUserDto;
import com.example.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException{
		User user = userService.findUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException{
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PutMapping("/follow/{followUserId}")
	public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfile(token);
		String message =  userService.followUser(user.getId(), followUserId);
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}
	
	@PutMapping("/unfollow/{unfollowUserId}")
	public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable Integer unfollowUserId, @RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfile(token);
		String message =  userService.unfollowUser(user.getId(), unfollowUserId);
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

	@GetMapping("/suggestions")
	public ResponseEntity<List<SuggestionUserDto>> suggestedUserHandler(@RequestHeader("Authorization") String token) throws UserException {
		Integer userId = userService.findUserProfile(token).getId();
		List<SuggestionUserDto> suggestionUserDtos = userService.getSuggestedUsers(userId);
		return new ResponseEntity<>(suggestionUserDtos,HttpStatus.OK);
	}

	@GetMapping("/followers")
	public ResponseEntity<List<UserDto>> getFollowersListHandler(@RequestHeader("Authorization") String token) throws UserException {
		Integer userId = userService.findUserProfile(token).getId();
		List<UserDto> followersList = userService.getFollowersList(userId);
		return new ResponseEntity<>(followersList, HttpStatus.OK);
	}

	@GetMapping("/following")
	public ResponseEntity<List<UserDto>> getFollowingUsersListHandler(@RequestHeader("Authorization") String token) throws UserException {
		Integer userId = userService.findUserProfile(token).getId();
		List<UserDto> followersList = userService.getFollowingUsersList(userId);
		return new ResponseEntity<>(followersList, HttpStatus.OK);
	}
	
	@GetMapping("/req")
	public ResponseEntity<User> findLoggedInUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfile(token);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/remove-follower")
	public ResponseEntity<MessageResponse> removeFollowerHandler(@RequestParam("followerId") Integer followerId, @RequestHeader("Authorization") String token) throws UserException {
		Integer userId = userService.findUserProfile(token).getId();
		String message =  userService.removeFollower(followerId, userId);
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}
	
	@GetMapping("/multipleUser/{userIds}")
	public ResponseEntity<List<User>> findUserByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException{
		List<User> users = userService.findUserByIds(userIds);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUserByQueryHandler(@RequestParam("query") String query) throws UserException{
		List<User> users = userService.searchUser(query);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}

	@PutMapping("/account/edit")
	public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException{
		User reqUser = userService.findUserProfile(token);
		User updatedUser = userService.updateUserDetails(user, reqUser);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}


}
