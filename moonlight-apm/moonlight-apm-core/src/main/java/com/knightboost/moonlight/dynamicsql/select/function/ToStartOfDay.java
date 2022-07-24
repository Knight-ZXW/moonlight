package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class ToStartOfDay<T> extends AbstractUniTypeFunction<T, ToStartOfDay<T>> {

    protected ToStartOfDay(BindableColumn<T> column) {
        super(column);
    }

    @Override
    protected ToStartOfDay<T> copy() {
        return new ToStartOfDay<>(column);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "toStartOfDay" + "(" + column.renderWithTableAlias(tableAliasCalculator) + ")";
    }

    public static <T> ToStartOfDay<T> from(BindableColumn<T> column) {
        return new ToStartOfDay<>(column);
    }
}
