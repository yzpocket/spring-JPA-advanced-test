package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //@OneToOne(mappedBy = "user")
    //private Food food;

    //public void addFood(Food food) { // 양방향 2 테스트 - 외래 키의 주인이 아닌 User 에서 Food 를 저장하기 위해 addFood() 메서드 추가
    //    this.food = food;
    //    food.setUser(this);
    //}
}