package com.study.festipal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;              //여기서 id가 없기에 게시글이 저장되지 않는 상황, 즉 로그인 기능부터 구현해야할듯?
                                    //어느정도 구상은 했으니
    private String title;

    private String content;

    private String imagename;

    private String imagepath;

    private Boolean boardcheck;

}
