package com.example.demo.modal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.dto.UserDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name="Post")
@Table(name="posts")
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="post_id")
	private Integer id;

	@Column(nullable = false)
	private String caption;

	@Column(nullable = false)
	private String image;

	private String location;

	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
//
//	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonIgnore
//	private List<Comment> comments;
//
//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinTable(name = "post_likes",
//			joinColumns = @JoinColumn(name = "post_id"),
//			inverseJoinColumns = @JoinColumn(name = "user_id"))
//	@JsonIgnore
//	private List<User> likedByUsers;

	@Override
	public String toString() {
		return "Post{" +
				"id=" + id +
				", caption='" + caption + '\'' +
				", image='" + image + '\'' +
				", location='" + location + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
	
}
