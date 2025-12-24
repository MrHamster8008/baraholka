package com.yagubkov.baraholka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaraholkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaraholkaApplication.class, args);
        System.out.println("=======ПРИЛОЖЕНИЕ ЗАПУЩЕНО======");
        System.out.println("Ссылка: http://localhost:8080");
        System.out.println("=================================");
    }
}