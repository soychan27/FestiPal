package com.study.festipal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class timetable {

    @Id
    private String id;

    private String image;
}
