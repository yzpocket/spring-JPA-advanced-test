package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //다대일 양방향 연관관계 맺기 복습 food : user = N : 1 이다. -> user는 List형태여야 하고, DB 테이블에 아래 필드는 생성되지 않는다.
    @OneToMany(mappedBy = "user") //외래 키의 주인의 필드를 맵핑 "user" 여기서 user는 Food에 있는 외래키 참조변수 user다.
    private List<Food> foodList = new ArrayList<>();
}