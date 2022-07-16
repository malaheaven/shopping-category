package com.musinsa.shoppingcategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ShoppingCategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCategoryApplication.class, args);
    }

    @PostConstruct
    public void construct() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
