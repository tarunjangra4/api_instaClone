package com.example.demo.modal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.dto.UserDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer Id;

	@Column(nullable = false)
	private String content;

	private LocalDateTime createdAt;

	@Column(name = "child_of")
	private Integer childOf;

	@Column(name="replied_to")
	private Integer repliedTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "comment_likes",   		// this will create a new table
			joinColumns = @JoinColumn(name = "comment_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> likedByUsers;
}
