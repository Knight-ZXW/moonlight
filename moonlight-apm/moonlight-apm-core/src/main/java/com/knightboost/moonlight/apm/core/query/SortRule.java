package com.knightboost.moonlight.apm.core.query;

import lombok.Data;

/*
 * 查询结果的排序规则
 *  Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@Data
public class SortRule {
    /**
     * 排序字段，等效于 sql中的 order by ${sortField}
     */
    private String sortField;

    public static final String ASC = "ascending";
    public static final String DESC = "descending";

    /**
     * 默认为升序
     */
    private String order =ASC;

    public boolean isAscending(){
        return ASC.equals(this.order) || "asc".equals(this.order);
    }

    public boolean isDescending(){
        return DESC.equals(this.order) ||"desc".equals(this.order);
    }
}
