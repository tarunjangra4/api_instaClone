package com.example.demo.modal;

import java.time.LocalDateTime;

import com.example.demo.dto.UserDto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="Stories")
@Data
public class Story {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	private String image;

	private String caption;

	private LocalDateTime timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
