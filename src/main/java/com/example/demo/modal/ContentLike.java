package com.example.demo.modal;

import com.example.demo.Enums.ContentType;
import jakarta.persistence.*;

@Entity
@Table(name = "content_likes")
public class ContentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Enumerated
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "content_id")
    private Integer contentId;
}
