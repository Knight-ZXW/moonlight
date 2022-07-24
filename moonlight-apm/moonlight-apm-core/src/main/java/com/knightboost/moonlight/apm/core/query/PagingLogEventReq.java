package com.knightboost.moonlight.apm.core.query;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * 日志分页查询
 *  Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
public class PagingLogEventReq extends BaseApmPagingReq{
    //
    private List<String> detailFields =new ArrayList<>();
}
