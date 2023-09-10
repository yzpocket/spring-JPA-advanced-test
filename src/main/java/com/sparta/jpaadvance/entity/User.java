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
    //@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST) //CascadeType을 통해 영속성 전이로 연관된 데이터들을 한번에 처리하고자함 //외래 키의 주인의 필드를 맵핑 "user" 여기서 user는 Food에 있는 외래키 참조변수 user다.
    //@OneToMany(mappedBy = "user") //CascadeType을 지워보고 삭제에서 실패되는 것을 확인해보자
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true) //orphanRemoval = true으로 연관관계 제거를 켜준다 //CascadeType을 여러개 걸 땐 {}중괄호로 이 부분 코드 처럼,
    private List<Food> foodList = new ArrayList<>();

    public void addFoodList(Food food) {
        this.foodList.add(food);
        food.setUser(this); // 외래 키 수동설정
    }
}