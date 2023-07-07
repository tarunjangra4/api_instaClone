package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppConfig {

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception{

		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
//			.requestMatchers(HttpMethod.POST,"/signup").permitAll()
			.requestMatchers(HttpMethod.GET, "/*").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
			.csrf().disable()
			.formLogin()
			.and()
			.cors(cors -> cors.disable())
			.httpBasic();

//		http.cors().and()
//				.httpBasic().disable()
//				.csrf().disable()
//				.authorizeRequests(auth -> auth.m)

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
