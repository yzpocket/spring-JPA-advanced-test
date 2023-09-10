package com.sparta.jpaadvance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//주문 시간을 추가 기능에 대한 JPA Auditing 기능 구현 부분
@EnableJpaAuditing
@SpringBootApplication
public class JpaAdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaAdvanceApplication.class, args);
    }

}
