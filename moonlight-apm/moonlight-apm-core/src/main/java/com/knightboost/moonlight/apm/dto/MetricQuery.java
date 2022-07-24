package com.knightboost.moonlight.apm.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 代表指标类型的查询
 *  Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@Data
public class MetricQuery {

    private String eventName;

    private String metricName;

    private String aggregateFunction;

    private Map<String,Object> params = new LinkedHashMap<>();

    /**
     * 返回结果的别名 ，等效于sql 中的 as
     */
    private String alias;
}
