package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    //다대일 양방향 연관관계 맺기 복습 food : user = N : 1 이다. -> food 가 외래키 주인이다.
    @ManyToOne(fetch = FetchType.LAZY) // default가 eager인데(~One이니까) Lazy로 바꿔봄 -> @Transactional 환경이 필수로 필요하다 -> 테스트로
    @JoinColumn(name = "user_id")
    private User user;
}