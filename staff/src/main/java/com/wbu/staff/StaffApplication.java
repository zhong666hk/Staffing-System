package com.wbu.staff;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.wbu")
@MapperScan("com.wbu.staff.*.mapper")
public class StaffApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaffApplication.class);

    }
}
