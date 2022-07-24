package com.knightboost.moonlight.apm.dto;

import com.knightboost.moonlight.apm.core.query.BaseApmPagingReq;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public class IssueListReq extends BaseApmPagingReq {
    private int state;
    private List<MetricQuery> metricQueries;

}
