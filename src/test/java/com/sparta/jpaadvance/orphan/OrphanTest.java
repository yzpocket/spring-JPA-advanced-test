package com.sparta.jpaadvance.orphan;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrphanTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void init() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Robbie");
        userList.add(user1);

        User user2 = new User();
        user2.setName("Robbert");
        userList.add(user2);
        userRepository.saveAll(userList);

        List<Food> foodList = new ArrayList<>();
        Food food1 = new Food();
        food1.setName("고구마 피자");
        food1.setPrice(30000);
        food1.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food1);

        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food2);

        Food food3 = new Food();
        food3.setName("후라이드 치킨");
        food3.setPrice(15000);
        food3.setUser(user1); // 외래 키(연관 관계) 설정
        foodList.add(food3);

        Food food4 = new Food();
        food4.setName("양념 치킨");
        food4.setPrice(20000);
        food4.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        foodRepository.saveAll(foodList);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("연관관계 제거")
    void test1() {
        // 고객 Robbie 를 조회합니다.
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // 연관된 음식 Entity 제거 : 후라이드 치킨
        Food chicken = null;
        for (Food food : user.getFoodList()) {
            if(food.getName().equals("후라이드 치킨")) {
                chicken = food;
            }
        }
        if(chicken != null) { // 후라이드 치킨이 있으면 '후라이드 치킨' 제거
            user.getFoodList().remove(chicken);
        }

        // 연관관계 제거 확인 // 하지만 DB에서는 치킨 주문이 사라지지 않았다. -> User로 가보자 -> orphanRemoval = true로 속성을 부여했다. 다시 실행해보자. -> 연관관계로 삭제되었다. 이것 자체가 cascade 기능이 포함되어있다. -> 여러군데에서 참조하는 경우 문제가 발생 할 수 있어서 주의해서 사용해야 한다.
        // cascade의 delete와 orphanRemoval은 연관되어있는 관계를 잘보고 오류가 발생 할 수있는 상황 등을 고려하여 구현해야 한다.
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }
    }

}