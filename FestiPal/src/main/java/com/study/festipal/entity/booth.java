package com.study.festipal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class booth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    private String title;

    private String imagename;

    private String imagepath;

    private String number;

    private LocalDateTime createdTime;

    private String content;

    public booth(){
        this.imagename = null;
        this.imagepath = null;
    }
}