package com.study.festipal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    private String title;

    private String content;

    private String imagename;

    private String imagepath;

    @Column(nullable = false)
    private Boolean boardcheck = false;  // 기본값 설정

    private LocalDateTime createdTime;  // 작성 시간 추가

    public board() {
        this.imagename = null;
        this.imagepath = null;
    }
}
