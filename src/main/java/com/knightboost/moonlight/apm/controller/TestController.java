package com.knightboost.moonlight.apm.controller;

import com.knightboost.moonlight.apm.mapper.mysql.TestDao;
import com.knightboost.moonlight.apm.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestDao testDao;

    @GetMapping("insert")
    public Object testInsert(){
        testDao.insert(new Test());
        return Boolean.TRUE;
    }
}
