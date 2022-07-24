package com.knightboost.moonlight.clickhouse;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public enum PRECISION {
    Decimal64("Decimal64","toDecimal64"),
    Float64("Float64","toFloat64"),
    Int64("Int64","toInt64");

    public final String name;
    public final String functionPrefix;

    PRECISION(String precision, String functionPrefix) {
        this.name = precision;
        this.functionPrefix = functionPrefix;
    }
}
