package com.knightboost.moonlight.apm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Configuration
public class Test {
    @Bean()
    public User testUser(){
        return new User();
    }
}
