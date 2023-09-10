package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.Order;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.OrderRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class OrderTest { //<- Food와 User의 N:M관계를 풀어내기 위해 직접 중간 테이블을 만든것이기 때문에 관계를 맺어주는 것을 이곳에서 한다.

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("중간 테이블 Order Entity 테스트")
    void test1() {

        User user = new User(); //유저를 만들었다.
        user.setName("Robbie");

        Food food = new Food(); //음식을 만들었다.
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        // 주문 저장
        Order order = new Order(); //유저와 음식의 관계를 만들기 위해 중간 테이블 주문을 만들었다
        order.setUser(user); // 외래 키(연관 관계) 설정 // 주문에 유저에 유저를 넣어뒀다. //이 외래키는 Order Entity에 있는데 살펴보면, 유저(다):주문(일) N:1로 맵핑되어있고, User Entity의 입장에선 1:N으로 Order의 user 변수에 맵핑되어 있다.
        order.setFood(food); // 외래 키(연관 관계) 설정 // 주문에 음식에 음식을 넣어뒀다. //이 외래키는 Order Entity에 있는데 살펴보면, 음식(다):주문(일) N:1로 맵핑되어있고, Food Entity의 입장에선 1:N으로 Order의 food 변수에 맵핑되어 있다.

        userRepository.save(user);
        foodRepository.save(food);
        orderRepository.save(order);
    }

    @Test
    @DisplayName("중간 테이블 Order Entity 조회")
    void test2() {
         //1번 주문 조회
        Order order = orderRepository.findById(1L).orElseThrow(NullPointerException::new);

         //order 객체를 사용하여 고객 정보 조회
        User user = order.getUser();
        System.out.println("user.getName() = " + user.getName());

         //order 객체를 사용하여 음식 정보 조회
        Food food = order.getFood();
        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());
    }
    // 1번 주문에 대한 정보 조회,
    // 주문자
    //user.getName() = Robbie
    // 주문 음식
    //food.getName() = 후라이드 치킨
    // 주문 가격
    //food.getPrice() = 15000.0
}