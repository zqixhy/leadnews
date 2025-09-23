package com.qiao.behavior;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qiao.behavior.mapper")
public class BehaviorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BehaviorApplication.class, args);
    }
}
