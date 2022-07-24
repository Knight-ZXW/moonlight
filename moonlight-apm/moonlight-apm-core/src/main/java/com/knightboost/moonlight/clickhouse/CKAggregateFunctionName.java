package com.knightboost.moonlight.clickhouse;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
public   interface CKAggregateFunctionName {
    String COUNT = "count";
    String DISTINCT_COUNT = "distinctCount";
    String AVG = "avg";
    String QUANTILE = "quantile";
    String QUANTILExact = "quantileExact";
    String COUNT_RANGE = "countRange";

}
