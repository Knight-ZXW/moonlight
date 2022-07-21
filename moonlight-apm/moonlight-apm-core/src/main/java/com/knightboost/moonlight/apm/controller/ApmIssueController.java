package com.knightboost.moonlight.apm.controller;

import com.knightboost.moonlight.apm.entity.ApmIssue;
import com.knightboost.moonlight.apm.service.ApmIssueService;
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

/*
 * Created by Knight-ZXW on 2022/7/17
 * email: nimdanoob@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/apm")
@Api(value = "issue", tags = "apm")
@RequiredArgsConstructor
public class ApmIssueController {
    {
        int i =0;
    }
    ApmIssueService apmIssueService;

    @ApiOperation(value = "测试创建Issue", notes = "issue")
    @PostMapping("/testCreateIssue")
    public R<Long> createIssue(@RequestBody ApmIssue apmIssue) {
        return R.success(apmIssueService.insertIssue(apmIssue));
    }


}
