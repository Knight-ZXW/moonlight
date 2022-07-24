package com.knightboost.moonlight.apm.core.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AggregateFunction {
    //Standard aggregate functions
    COUNT("count"),
    MIN("min"),
    MAX("max"),
    SUM("sum"),
    AVG("avg"),
    ANY("any"),
    COVAR_SAMP("covarSamp"),
    //ClickHouse-specific aggregate functions:
    QUANTILE("quantile"),
    QUANTILES("quantiles"),
    ;

    private String name;


}
