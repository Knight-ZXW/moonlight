package com.knightboost.moonlight.apm.controller;

import com.knightboost.moonlight.apm.config.User;
import com.knightboost.moonlight.apm.entity.LogEvent;
import com.knightboost.moonlight.apm.service.LogEventQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;

import java.util.Date;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/apm/debug")
@Api(value = "logevent", tags = "apm")
@RequiredArgsConstructor
public class DebugController {
    final LogEventQueryService logEventService;
    final User user;


    @ApiOperation(value = "测试写入日志", notes = "issue")
    @PostMapping("/insertLog")
    public R<Boolean> createIssue(@RequestBody LogEvent logEvent) {
        logEvent.setEventTime(new Date(logEvent.getTimestamp()));
        logEventService.insertLog(logEvent);
        return R.success();
    }
}
