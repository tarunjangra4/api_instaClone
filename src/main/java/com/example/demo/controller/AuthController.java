package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exceptions.UserException;
import com.example.demo.modal.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin
public class AuthController {
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
