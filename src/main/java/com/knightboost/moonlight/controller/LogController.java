package com.knightboost.moonlight.controller;

import com.knightboost.moonlight.common.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @PostMapping("upload")
    public ApiResult<Boolean> uploadLog(){
        return ApiResult.success();
    }

}
