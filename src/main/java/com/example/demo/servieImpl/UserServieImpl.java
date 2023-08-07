package com.example.demo.servieImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.SuggestionUserDto;
import com.example.demo.modal.Follows;
import com.example.demo.repo.FollowsRepo;
import com.example.demo.security.JwtTokenClaims;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private FollowsRepo followsRepo;

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
		User followingUser = findUserById(followedUserId);

//		UserDto follower = new UserDto();
//		follower.setEmail(reqUser.getEmail());
//		follower.setId(reqUser.getId());
//		follower.setName(reqUser.getName());
//		follower.setUserImage(reqUser.getUserImage());
//		follower.setUsername(reqUser.getUsername());

//		UserDto following = new UserDto();
//		following.setEmail(followUser.getEmail());
//		following.setId(followUser.getId());
//		following.setName(followUser.getName());
//		following.setUserImage(followUser.getUserImage());
//		following.setUsername(followUser.getUsername());

		Follows f = new Follows();
		f.setFollower(reqUser);
		f.setFollowing(followingUser);

		followsRepo.save(f);
		return "you are following "+followingUser.getUsername();
	}

	@Override
	public String unfollowUser(Integer reqUserId, Integer unfollowedUserId) throws UserException {
		User unfollowUser = findUserById(unfollowedUserId);
		followsRepo.unfollowUser(reqUserId, unfollowedUserId);
		return "You have unfollowed "+unfollowUser.getUsername();
	}

	@Override
	public String removeFollower(Integer reqUserId, Integer unfollowedUserId) throws UserException {
		User unfollowUser = findUserById(unfollowedUserId);
		followsRepo.unfollowUser(reqUserId, unfollowedUserId);
		return "You have removed "+unfollowUser.getUsername()+" as follower.";
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
		if(updateUserDetails.getId()!=existingUserDetails.getId()){
			throw new UserException("You can't edit someone else's profile");
		}
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
		if(updateUserDetails.getUserImage()!=null) {
			existingUserDetails.setUserImage(updateUserDetails.getUserImage());
		}
		if(updateUserDetails.getPassword()!=null) {
			existingUserDetails.setPassword(passwordEncoder.encode(updateUserDetails.getPassword()));
		}
		if(updateUserDetails.getId().equals(existingUserDetails.getId())) {
			return userRepo.save(existingUserDetails);
		}
		throw new UserException("You can't update this user");
	}

//	@Override
//	public List<SuggestionUserDto> getSuggestedUsers(Integer userId) {
//		List<Integer> userIds = followsRepo.getSuggestedUserList(userId);
//		System.out.println("getSuggestedUserList "+userIds);
//		List<SuggestionUserDto> suggestionUserDtos = new ArrayList<>();
//		for(Integer id : userIds){
//			suggestionUserDtos.add(userRepo.getSuggestedUserDetails(id, userId));
//		}
//		return suggestionUserDtos;
//	}

	@Override
	public List<SuggestionUserDto> getSuggestedUsers(Integer userId) {
		List<Integer> followingUserIds = followsRepo.findFollowingUsersByUserId(userId);
		followingUserIds.add(userId);
		List<SuggestionUserDto> suggestionUserDtos = new ArrayList<>();
		suggestionUserDtos.addAll(userRepo.getSuggestedUserDetails(followingUserIds));

		for(SuggestionUserDto suggestedUserDto : suggestionUserDtos){
			List<Integer> followers = followsRepo.getFollowersIdByUserId(userId);
			suggestedUserDto.setIsFollowing(followers.contains(suggestedUserDto.getUserId()));
		}

		return suggestionUserDtos;
	}

	@Override
	public List<UserDto> getFollowersList(Integer userId) {
		List<UserDto> followers = userRepo.getFollowersList(userId);
		return followers;
	}

	@Override
	public List<UserDto> getFollowingUsersList(Integer userId) {
		List<UserDto> followers = userRepo.getFollowingUsersList(userId);
		return followers;
	}
}
