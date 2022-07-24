package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class ToStartOfMinute<T> extends AbstractUniTypeFunction<T, ToStartOfMinute<T>> {

    protected ToStartOfMinute(BindableColumn<T> column) {
        super(column);
    }

    @Override
    protected ToStartOfMinute<T> copy() {
        return new ToStartOfMinute<>(column);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "toStartOfMinute" + "(" + column.renderWithTableAlias(tableAliasCalculator) + ")";
    }

    public static <T> ToStartOfMinute<T> from(BindableColumn<T> column) {
        return new ToStartOfMinute<>(column);
    }
}
