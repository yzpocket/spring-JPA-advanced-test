package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    //@OneToOne
    //@JoinColumn(name = "user_id")
    //private User user;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;

    //@OneToMany
    //@JoinColumn(name = "food_id") // users 테이블에 food_id 컬럼
    //private List<User> userList = new ArrayList<>();

    //@ManyToMany
    //@JoinTable(name = "orders", // 중간 테이블 생성
    //        joinColumns = @JoinColumn(name = "food_id"), // 현재 위치인 Food Entity 에서 중간 테이블로 조인할 컬럼 설정
    //        inverseJoinColumns = @JoinColumn(name = "user_id")) // 반대 위치인 User Entity 에서 중간 테이블로 조인할 컬럼 설정
    //private List<User> userList = new ArrayList<>();

    //@ManyToMany
    //@JoinTable(name = "orders", // 중간 테이블 생성
    //        joinColumns = @JoinColumn(name = "food_id"), // 현재 위치인 Food Entity 에서 중간 테이블로 조인할 컬럼 설정
    //        inverseJoinColumns = @JoinColumn(name = "user_id")) // 반대 위치인 User Entity 에서 중간 테이블로 조인할 컬럼 설정
    //private List<User> userList = new ArrayList<>();
    //
    //public void addUserList(User user) {
    //    this.userList.add(user); // 외래 키(연관 관계) 설정
    //    user.getFoodList().add(this);
    //}

    // 중간 테이블을 통해 Food와 User의 관계를 맺기 위해서 맵핑한것
    // 하지만 Food 쪽에서 User를 조회할 필요가 없다면?
    // -> 아래 관계를 맺어주는, 이는 곧 양방향으로 만들어주는 아래 맵핑이 필요가 없을 수도 있다.
    // 아래 맵핑을 통해 User가 키주인일 경우 User에 있는 food 변수를 지칭하는 것이엇을 것이다.
    // 하지만 현재는, 중간 테이블이 그 키주인 역할을 해주고 있기 때문에 아래 food는 Order테이블에 있으며, 그것은 User를 참조하게 된다.
    @OneToMany(mappedBy = "food")
    private List<Order> orderList = new ArrayList<>(); // 따라서 현재 Food는 Order통해서 User를 조회 할 수 있다.  -> 아보카도 음식을 주문한 사람들은? 1,2,...
}