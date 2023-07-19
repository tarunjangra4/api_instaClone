package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repo.UserRepo;

@Service
public class UserUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.example.demo.modal.User> opt = userRepo.findByEmail(username);
		if(opt.isPresent()) {
			com.example.demo.modal.User user = opt.get();
			
			List<GrantedAuthority> authorities  = new ArrayList<>();
			
			return new User(user.getEmail(), user.getPassword(), authorities);
		}
		throw new BadCredentialsException("user not found with username "+username);
	}

}
