package com.musinsa.shoppingcategory.constants;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

public class BaseConfig {

    @Configuration
    @EnableJpaAuditing
    public static class JpaConfig {

    }

    @Configuration
    @EnableCaching
    public static class CacheConfig {

    }
}
