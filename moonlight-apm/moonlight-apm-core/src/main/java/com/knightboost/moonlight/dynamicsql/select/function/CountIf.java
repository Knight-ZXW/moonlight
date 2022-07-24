package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/5/12
 * email: nimdanoob@163.com
 */
public class CountIf<T> extends AbstractUniTypeFunction<T, CountIf<T>> {


    private IfCondition condition;

    public CountIf(BindableColumn<T> column,
                   IfCondition condition) {
        super(column);
        this.condition =condition;
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "countIf(" + column.renderWithTableAlias(tableAliasCalculator) + ", " + condition.render(
                column.renderWithTableAlias(tableAliasCalculator)
        ) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected CountIf<T> copy() {
        return new CountIf<T>(column, condition);
    }


    public static <T> CountIf<T> rangeIn(BindableColumn<T> column,
                                         IfCondition condition) {
        return new CountIf<T>(column, condition);
    }
}
