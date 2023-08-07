package com.example.demo.modal;

import jakarta.persistence.*;
import com.example.demo.modal.User;
import lombok.Data;

@Entity
@Table(name="follows")
@Data
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

}
