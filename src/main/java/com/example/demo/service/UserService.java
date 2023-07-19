package com.example.demo.service;

import java.util.List;

import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;

public interface UserService {
	
	public User registerUser(User user) throws UserException;
	
	public User findUserById(Integer userId) throws UserException; 
	
	public User findUserProfile(String token) throws UserException;
	
	public User findUserByUsername(String userName) throws UserException;
	
	public String followUser(Integer reqUserId, Integer followedUserId) throws UserException;
	
	public String unfollowUser(Integer reqUserId, Integer followedUserId) throws UserException;
	
	public List<User> findUserByIds(List<Integer> userIds) throws UserException;
	
	public List<User> searchUser(String query) throws UserException;
	
	public User updateUserDetails(User updateUserDetails, User existingUserDetails) throws UserException;
}
