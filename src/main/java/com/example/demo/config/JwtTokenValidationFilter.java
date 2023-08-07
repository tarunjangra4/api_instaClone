package com.example.demo.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(SecurityContext.HEADER);
		System.out.println("validation filter "+jwt);
		if(jwt!=null) {
			try {
				jwt = jwt.substring(7);

				SecretKey secret_key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
				Claims claims = Jwts.parserBuilder().setSigningKey(secret_key).build().parseClaimsJws(jwt).getBody();

				String username = String.valueOf(claims.get("username"));
				System.out.println("username "+username);
				String authorities = (String)claims.get("authorities");
				System.out.println("authorities "+authorities);
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				System.out.println("auths "+auths);
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, auths);
				System.out.println("auth "+auth);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}catch(Exception e) {
				throw new BadCredentialsException("invalid token...");
			}
		}
		filterChain.doFilter(request, response);
		return;
	}
	
	protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException{
		return req.getServletPath().equals("/signin");
	}

}
