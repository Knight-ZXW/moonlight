package com.knightboost.moonlight.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
@RestController
@RequestMapping("/admin")
public class TestController {

    @GetMapping("/test")
    public R test(){
        return R.success();
    }
}
