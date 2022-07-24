package com.knightboost.moonlight.apm.core.query;

import lombok.Data;

import java.util.List;

/*
 * APM 数据查询 结果为分页的 基类模型
 *  Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@Data
public class BaseApmPagingReq extends BaseApmReq {
    /**
     * 查询的页码
     */
    private Integer pageNo = 0;

    /**
     * 每页数量
     */
    private Integer pageSize = 20;


    private List<SortRule> sortRules;
}
