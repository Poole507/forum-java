package com.smartsignlanguage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.smartsignlanguage.mapper")
public class SmartSignLanguageApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartSignLanguageApplication.class, args);
    }

}
