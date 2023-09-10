package com.sparta.jpaadvance.cascade;

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

@SpringBootTest
public class CascadeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @DisplayName("Robbie 음식 주문")
    void test1() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        User user = new User();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food); //주문 성공

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2); //주문 성공

        //save 3개씩하는것이 뭔가 비효율적이다. 아래로.. 영속성 전이를 이용하자
        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }
    //위 같은 상황에서 DB에서의 ON CASECADE기능을 JPA에서도 제공한다. 연관된 데이터를 묶어서 하는것
    //JPA에서는 영속성 전이로 영속 상태의 Entity들이 저장 될 때, 연관된 데이터들도 자동으로 적용시키는것.
    // user를 저장하면 food, food2 모두 영속성이 전파되서 저장하길 원하는 것.
    @Test
    @DisplayName("영속성 전이 저장")
    void test2() {
        // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
        User user = new User();
        user.setName("Robbie");

        // 후라이드 치킨 주문
        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        user.addFoodList(food);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        user.addFoodList(food2);

        //위 테스트와 다르게 영속성 전이 cascade 타입을 주면서 user만 저장하는데 관련된 데이터들이 함께 저장된다.
        userRepository.save(user);
    }

    //삭제도 해보자. 만약 주문 정보들이 저장된 회원user가 탈퇴한다고 가정하면,
    //user를 지우면 user와 관련된 주문 정보도 함께 삭제되어야 한다 -> 관련된 데이터들을 함께 삭제하면서 무결성을 보장
    @Test
    @Transactional // <- Lazy로딩 <- OneToMany기 때문에 @Transactional 환경이 필요하다.
    @Rollback(value = false)
    @DisplayName("Robbie 탈퇴")
    void test3() {
        // 고객 Robbie 를 조회합니다.
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // Robbie 가 주문한 음식 조회
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }

        // 주문한 음식 데이터 삭제
        foodRepository.deleteAll(user.getFoodList()); // <- getFoodList()는 @OneToMany로 기본 상태가 Lazy다 따라서 위에 @Transactional 환경이 필요하다.

        // Robbie 탈퇴
        userRepository.delete(user);
    }

    // 이것도 마찬가지로 영속성 전이 삭제로 더 간편하게 만들어보자.
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("영속성 전이 삭제")
    void test4() {
        // 고객 Robbie 를 조회합니다.
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        // Robbie 가 주문한 음식 조회
        for (Food food : user.getFoodList()) {
            System.out.println("food.getName() = " + food.getName());
        }

        // Robbie 탈퇴
        userRepository.delete(user); //주문한 음식 데이터 삭제 없이 유저만 탈퇴시켜본다. 그럼 관련된 데이터인 주문한 음식 데이터가 같이 사라지는지 볼 수 있다.
        // user만 지웠는데 관련 데이터도 모두 delete했다.
    }
}