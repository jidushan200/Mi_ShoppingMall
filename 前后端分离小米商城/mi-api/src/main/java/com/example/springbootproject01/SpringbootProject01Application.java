package com.example.springbootproject01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication()
@MapperScan("com.example.springbootproject01.mapper")
public class SpringbootProject01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProject01Application.class, args);
    }

}
