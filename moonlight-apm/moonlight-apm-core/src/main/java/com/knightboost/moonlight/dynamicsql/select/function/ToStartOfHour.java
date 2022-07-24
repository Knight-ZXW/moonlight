package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class ToStartOfHour<T> extends AbstractUniTypeFunction<T, ToStartOfHour<T>> {

    protected ToStartOfHour(BindableColumn<T> column) {
        super(column);
    }

    @Override
    protected ToStartOfHour<T> copy() {
        return new ToStartOfHour<>(column);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "toStartOfHour"+"("+column.renderWithTableAlias(tableAliasCalculator)+")";
    }

    public static <T> ToStartOfHour<T> from(BindableColumn<T> column) {
        return new ToStartOfHour<>(column);
    }
}
