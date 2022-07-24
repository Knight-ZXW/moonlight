package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/5/12
 * email: nimdanoob@163.com
 */
public class AvgIf<T> extends AbstractUniTypeFunction<T, AvgIf<T>> {

    private String condition;

    private AvgIf(BindableColumn<T> column, String condition) {
        super(column);
        this.condition = condition;
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "avgIf(" + column.renderWithTableAlias(tableAliasCalculator)
                + ", " + condition + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected AvgIf<T> copy() {
        return new AvgIf<T>(column, condition);
    }

    public static <T> AvgIf<T> of(BindableColumn<T> column, String condition) {
        return new AvgIf<T>(column, condition);
    }




}
