package com.study.festipal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String password;

    private String name;

    private String phone;

    private Integer authority;

}
