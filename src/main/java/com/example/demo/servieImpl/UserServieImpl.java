package com.example.demo.servieImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.security.JwtTokenClaims;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@Service
public class UserServieImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public User registerUser(User user) throws UserException {
		Optional<User> isEmailExist = userRepo.findByEmail(user.getEmail());
		if(isEmailExist.isPresent()) {
			throw new UserException("Email is already exist.");
		}
		
		Optional<User> isUserNameExist = userRepo.findByUsername(user.getUsername());
		if(isUserNameExist.isPresent()) {
			throw new UserException("Email is already taken.");
		}
		
		if(user.getEmail()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null) {
			throw new UserException("All fields are required");
		}
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setUsername(user.getUsername());
		newUser.setName(user.getName());
//		newUser.setPassword(user.getPassword());
		
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepo.save(newUser);
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		Optional<User> user = userRepo.findById(userId); 
		
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("user not exist with id: "+userId);
	}

	@Override
	public User findUserProfile(String token) throws UserException {

		token = token.substring(7);
		JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
		String email = jwtTokenClaims.getUsername();
		Optional<User> opt= userRepo.findByEmail(email);
		if(opt.isPresent()){
			return opt.get();
		}
		throw new UserException("invalid token...");
	}

	@Override
	public User findUserByUsername(String username) throws UserException {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("user not exist with username :"+username);
	}

	@Override
	public String followUser(Integer reqUserId, Integer followedUserId) throws UserException {
		User reqUser = findUserById(reqUserId);
		User followUser = findUserById(followedUserId);
		
		UserDto follower = new UserDto();
		follower.setEmail(reqUser.getEmail());
		follower.setId(reqUser.getId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getUserImage());
		follower.setUsername(reqUser.getUsername());
		
		UserDto following = new UserDto();
		following.setEmail(followUser.getEmail());
		following.setId(followUser.getId());
		following.setName(followUser.getName());
		following.setUserImage(followUser.getUserImage());
		following.setUsername(followUser.getUsername());

		reqUser.getFollowing().add(following);
		followUser.getFollower().add(follower);
		
		userRepo.save(followUser);
		userRepo.save(reqUser);
		return "you are following "+followUser.getUsername();
	}

	@Override
	public String unfollowUser(Integer reqUserId, Integer unfollowedUserId) throws UserException {
		User reqUser = findUserById(reqUserId);
		User unfollowUser = findUserById(unfollowedUserId);
		
		UserDto follower = new UserDto();
		follower.setEmail(reqUser.getEmail());
		follower.setId(reqUser.getId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getUserImage());
		follower.setUsername(reqUser.getUsername());
		
		UserDto unfollowing = new UserDto();
		unfollowing.setEmail(unfollowUser.getEmail());
		unfollowing.setId(unfollowUser.getId());
		unfollowing.setName(unfollowUser.getName());
		unfollowing.setUserImage(unfollowUser.getUserImage());
		unfollowing.setUsername(unfollowUser.getUsername());
		
		reqUser.getFollowing().remove(unfollowing);
		unfollowUser.getFollower().remove(follower);
		
		userRepo.save(reqUser);
		userRepo.save(unfollowUser);
		return "You have unfollowed "+unfollowUser.getUsername();
	}

	@Override
	public List<User> findUserByIds(List<Integer> userIds) throws UserException {
		List<User> listOfUsers = userRepo.findAllUsersByUserIds(userIds);
		
		return listOfUsers;
	}

	@Override
	public List<User> searchUser(String query) throws UserException {
		List<User> listOfUsers = userRepo.findByQuery(query);
		if(listOfUsers.size()==0) {
			throw new UserException("user not found");
		}
		return listOfUsers;
	}

	@Override
	public User updateUserDetails(User updateUserDetails, User existingUserDetails) throws UserException {
		if(updateUserDetails.getEmail()!=null) {
			existingUserDetails.setEmail(updateUserDetails.getEmail());
		}
		if(updateUserDetails.getBio()!=null) {
			existingUserDetails.setBio(updateUserDetails.getBio());
		}
		if(updateUserDetails.getName()!=null) {
			existingUserDetails.setName(updateUserDetails.getName());
		}
		if(updateUserDetails.getUsername()!=null) {
			existingUserDetails.setUsername(updateUserDetails.getUsername());
		}
		if(updateUserDetails.getMobile()!=null) {
			existingUserDetails.setMobile(updateUserDetails.getMobile());
		}
		if(updateUserDetails.getGender()!=null) {
			existingUserDetails.setGender(updateUserDetails.getGender());
		}
		if(updateUserDetails.getWebsite()!=null) {
			existingUserDetails.setWebsite(updateUserDetails.getWebsite());
		}
		if(updateUserDetails.getUserImage()!=null) {
			existingUserDetails.setUserImage(updateUserDetails.getUserImage());
		}
		if(updateUserDetails.getId().equals(existingUserDetails.getId())) {
			return userRepo.save(existingUserDetails);
		}
		throw new UserException("You can't update this user");
	}

}
