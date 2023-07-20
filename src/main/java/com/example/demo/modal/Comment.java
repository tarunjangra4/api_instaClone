package com.example.demo.modal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.dto.UserDto;

import jakarta.persistence.*;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="id",column=@Column(name="user_id")),
		@AttributeOverride(name="email",column=@Column(name="user_email"))
	})
	private UserDto user;

	@ManyToOne
	private Post post;
	
	private String content;


//	private Integer postId;
	
	@Embedded
	@ElementCollection
	private Set<UserDto> likedByUsers = new HashSet<>();
	
	private LocalDateTime createdAt;

	public Comment() {
	}

	public Comment(Integer id, UserDto user, String content, Set<UserDto> likedByUsers, LocalDateTime createdAt) {
		super();
		Id = id;
		this.user = user;
		this.content = content;
		this.likedByUsers = likedByUsers;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<UserDto> getLikedByUsers() {
		return likedByUsers;
	}

	public void setLikedByUsers(Set<UserDto> likedByUsers) {
		this.likedByUsers = likedByUsers;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
