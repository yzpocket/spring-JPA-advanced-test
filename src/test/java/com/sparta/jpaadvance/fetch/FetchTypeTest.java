package com.sparta.jpaadvance.fetch;

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
public class FetchTypeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void init() { // 지연로딩 테스트를 위해 아래 DB 데이터 넣은 용도임
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Robbie");
        userList.add(user1);

        User user2 = new User();
        user2.setName("Robbert");
        userList.add(user2);
        userRepository.saveAll(userList); //한번에 user1, user2를 리스트에 담아서 saveAll로 저장

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
        food4.setName("후라이드 치킨");
        food4.setPrice(15000);
        food4.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        foodRepository.saveAll(foodList);
    }

    // JPA에서는 fetch type에 따라서 가져오는 방식을 2가지로 구분 할 수 있다. LAZY(지연로딩), EAGER(즉시로딩)

    // [1] 지연로딩(LAZY) : 필요한 시점에 정보를 가져온다.
    // @OneToMany는 기본 default 는 Lazy다.
    // @ ~ Many인 어노테이션들은 default 는 Lazy다.
    // - 지연 로딩이 된 Entity의 정보를 조회할때는 반드시 영속성 Persistence Context -> @Transactional 환경이 적용되어 있어야 한다.
    // - 조회 할 때는 원래 영속성 컨텍스트, @Transactional 환경이 필수가 아니었지만, 지연 로딩(LAZY) 기능을 사용하고자 할땐 반드시 @Transactional 상태여야 한다.

    // [2] 즉시로딩(EAGER) : 즉시 정보를 가져온다.
    // @ManyToOne은 기본 default 가 Eager다.
    // @ ~ One인 어노테이션들은 default 가 Eager다.

    @Test
    @Transactional // LAZY로딩 일때는 반드시 영속성 컨텍스트에서 관리를 받고 있어야 한다.
    @DisplayName("아보카도 피자 조회 성공 -> LAZY 로딩인데 @Transactional걸어서 영속성컨텍스트가 Entity 관리함")
    void test1() {
        Food food = foodRepository.findById(2L).orElseThrow(NullPointerException::new);

        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());

        System.out.println("아보카도 피자를 주문한 회원 정보 조회");
        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }

    @Test
    //@Transactional
    @DisplayName("Robbie 고객 조회 실패 -> LAZY 로딩인데 @Transactional 없어서 영속성컨텍스트가 Entity 관리못함") //
    void test2() {
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }
}