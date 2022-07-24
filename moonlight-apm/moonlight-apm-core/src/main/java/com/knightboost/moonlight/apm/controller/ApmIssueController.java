package com.knightboost.moonlight.apm.controller;

import com.knightboost.moonlight.apm.core.query.BaseApmReq;
import com.knightboost.moonlight.apm.core.query.PagingLogEventReq;
import com.knightboost.moonlight.apm.dto.IssueListReq;
import com.knightboost.moonlight.apm.entity.ApmIssue;
import com.knightboost.moonlight.apm.service.ApmIssueService;
import com.knightboost.moonlight.apm.service.LogEventQueryService;
import com.knightboost.moonlight.apm.vo.ApmIssueListItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
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
        int i = 0;
    }

    private final ApmIssueService apmIssueService;
    private final LogEventQueryService logEventQueryService;

    @ApiOperation(value = "测试创建Issue", notes = "issue")
    @PostMapping("/testCreateIssue")
    public R<Long> createIssue(@RequestBody ApmIssue apmIssue) {
        return R.success(apmIssueService.insertIssue(apmIssue));
    }

    @PostMapping("issue/list")
    public R<PageImpl<ApmIssueListItem>> pagingIssues(@RequestBody IssueListReq req) {
        return R.success(apmIssueService.pagingQueryIssues(req));
    }

    @PostMapping("logEvents")
    public R pagingLogEvents(@RequestBody PagingLogEventReq req) {
        return R.success(logEventQueryService.pagingQueryApmLogEvent(req));
    }

    /**
     * 日志/问题 Metric 趋势
     * @param req
     * @return
     */
    @PostMapping("eventMetricTrend")
    public R eventMetricTrend(@RequestBody BaseApmReq req){
        return R.success(logEventQueryService.metricAggregateByTime(req));
    }




}
