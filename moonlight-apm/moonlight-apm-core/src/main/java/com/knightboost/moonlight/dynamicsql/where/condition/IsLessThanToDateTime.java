package com.knightboost.moonlight.dynamicsql.where.condition;

import org.mybatis.dynamic.sql.where.condition.IsGreaterThanOrEqualTo;

/*
 * Created by Knight-ZXW on 2022/7/24
 * email: nimdanoob@163.com
 */
public class IsLessThanToDateTime<T> extends IsGreaterThanOrEqualTo<T> {
    protected IsLessThanToDateTime(T value) {
        super(value);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " < " + "toDateTime("+ placeholder+")"; //$NON-NLS-1$
    }

    public static <T> IsLessThanToDateTime<T> of(T value) {
        return new IsLessThanToDateTime<>(value);
    }
}
