package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class Distinct<T> extends AbstractUniTypeFunction<T,Distinct<T>> {
    protected Distinct(BindableColumn<T> column) {
        super(column);
    }

    @Override
    protected Distinct<T> copy() {
        return new Distinct<>(column);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "distinct "+column.renderWithTableAlias(tableAliasCalculator);
    }

    public static <T> Distinct<T> of(BindableColumn<T> column){
        return new Distinct<>(column);
    }
}
