package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> followUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token) throws UserException {
		System.out.println("follow user calledx");
		User user = userService.findUserProfile(token);
		String message =  userService.followUser(user.getId(), followUserId);

//		MessageResponse res = new MessageResponse(message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@PutMapping("/unfollow/{unfollowUserId}")
	public ResponseEntity<String> unfollowUserHandler(@PathVariable Integer unfollowUserId, @RequestHeader("Authorization") String token) throws UserException {
		System.out.println("unfollow user calledx");
		User user = userService.findUserProfile(token);
		String message =  userService.unfollowUser(user.getId(), unfollowUserId);

//		MessageResponse res = new MessageResponse(message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping("/req")
	public ResponseEntity<User> findLoggedInUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
		User user = userService.findUserProfile(token);
		return new ResponseEntity<>(user, HttpStatus.OK);
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
