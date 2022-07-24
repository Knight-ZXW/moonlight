package com.knightboost.moonlight.apm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.knightboost.moonlight.apm.entity.ApmIssue;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@Data
public class ApmIssueListItem extends ApmIssue {
    /**
     * issue 对应的发生次数， 数据来源于 clickhouse 中hash值对应的日志数
     */
    @TableField(exist = false)
    private Long count;

    /**
     * issue 对应的影响设备数
     */
    @TableField(exist = false)
    private Long deviceCount;

    @TableField(exist = false)
    private Map<String,Number> metrics =new HashMap<>();
}
