package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order { // <- Food와 User의 N:M관계를 풀어내기 위해 직접 중간 테이블을 만든것이기 때문에 관계를 맺어주는 것을 이곳에서 한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키의 주인이 외래키를 가지고 있는것이 좋다
    //현재는 중간 테이블이 그 중간역할을 해주고 있기 때문에 아래 처럼 둘다 가지고 있는 것이다.
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}