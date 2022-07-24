package com.knightboost.moonlight.dynamicsql.where.condition;

import org.mybatis.dynamic.sql.where.condition.IsGreaterThanOrEqualTo;

/*
 * Created by Knight-ZXW on 2022/7/24
 * email: nimdanoob@163.com
 */
public class IsGreaterThanOrEqualToDateTime<T> extends IsGreaterThanOrEqualTo<T> {
    protected IsGreaterThanOrEqualToDateTime(T value) {
        super(value);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " >= " + "toDateTime("+ placeholder+")"; //$NON-NLS-1$
    }

    public static <T> IsGreaterThanOrEqualToDateTime<T> of(T value) {
        return new IsGreaterThanOrEqualToDateTime<>(value);
    }
}
