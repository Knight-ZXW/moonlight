package com.knightboost.moonlight.apm.core.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
public class GroupAggregateInfo {
    private Long groupId;

    private Map<String,Number> metrics =new HashMap<>();
}
