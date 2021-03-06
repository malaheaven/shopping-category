package com.musinsa.shoppingcategory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommonController {

    @GetMapping("/health")
    public String health() {
        log.info("health_check");
        return HttpStatus.OK.name();
    }

}
