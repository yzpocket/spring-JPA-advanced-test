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

    //@OneToOne(mappedBy = "user")
    //private Food food;

    //public void addFood(Food food) { // 양방향 2 테스트 - 외래 키의 주인이 아닌 User 에서 Food 를 저장하기 위해 addFood() 메서드 추가
    //    this.food = food;
    //    food.setUser(this);
    //}

    //@OneToMany(mappedBy = "user")
    //private List<Food> foodList = new ArrayList<>();
    //
    //public void addFoodList(Food food) {
    //    this.foodList.add(food);
    //    food.setUser(this); // 외래 키(연관 관계) 설정
    //}

    //@ManyToMany(mappedBy = "userList")
    //private List<Food> foodList = new ArrayList<>();
    //
    //public void addFoodList(Food food) {
    //    this.foodList.add(food);
    //    food.getUserList().add(this); // 외래 키(연관 관계) 설정
    //}

    // 중간 테이블을 통해 Food와 User의 관계를 맺기 위해서 맵핑한것
    // 하지만 User 쪽에서 Food를 조회할 필요가 없다면?
    // -> 아래 관계를 맺어주는, 이는 곧 양방향으로 만들어주는 아래 맵핑이 필요가 없을 수도 있다.
    // 아래 맵핑을 통해 Food가 키주인일 경우 Food에 있는 user 변수를 지칭하는 것이엇을 것이다.
    // 하지만 현재는, 중간 테이블이 그 키주인 역할을 해주고 있기 때문에 아래 user는 Order테이블에 있으며, 그것은 Food를 참조하게 된다.
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>(); // 따라서 현재 User는 Order통해서 Food를 조회 할 수 있다. -> Robbie가 주문한 음식들은? 1,2,3,...
}