package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin
//@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"}, allowCredentials = "true")
public class AuthController {

//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	private AuthenticationManager manager;
//
//	@Autowired
//	private JwtHelper helper;
//
//	private Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//	@PostMapping("login")
//	public ResponseEntity<JwtReponse> login(@RequestBody JwtRequest jwtRequest){
//		this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
//		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
//		String token = this.helper.generateToken(userDetails);
//		JwtReponse response = JwtReponse.builder()
//				.jwtToken(token)
//				.username(userDetails.getUsername()).build();
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	private void doAuthenticate(String email, String password){
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//		try {
//			manager.authenticate(authentication);
//		}catch (BadCredentialsException e){
//			throw new RuntimeException("Invalid Username or Password");
//		}
//	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@PostMapping("/signup")
	public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException{
		User createdUser = userService.registerUser(user);
		return new ResponseEntity<User>(createdUser,HttpStatus.OK);
	}

	@GetMapping("/signin")
	public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException{
		Optional<User> opt = userRepo.findByEmail(auth.getName());
		if(opt.isPresent()) {
			return new ResponseEntity<User>(opt.get(),HttpStatus.ACCEPTED);
		}
		throw new BadCredentialsException("invalid username or password");
	}
}
