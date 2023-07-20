package com.example.demo.modal;

import jakarta.persistence.*;
import com.example.demo.modal.User;

@Entity
@Table(name="follows")
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "followed_user")
    private User followedUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
