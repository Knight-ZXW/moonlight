package com.knightboost.moonlight.dynamicsql.select.aggregate;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class QuantileExact<T> extends AbstractUniTypeFunction<T, QuantileExact<T>> {

    private float level;

    /**
     * @param column
     * @param level  level — Level of quantile. Optional parameter. Constant floating-point number from 0 to 1.
     *               We recommend using a level value in the range of [0.01, 0.99].
     *               Default value: 0.5. At level=0.5 the function calculates median.
     */
    protected QuantileExact(BindableColumn<T> column, float level) {
        super(column);
        this.level = level;
    }

    @Override
    protected QuantileExact<T> copy() {
        return new QuantileExact<>(column, level);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "quantileExact" + "(" + level + ")" + "(" + column.renderWithTableAlias(tableAliasCalculator) + ")";
    }

    public static <T> QuantileExact<T> of(BindableColumn<T> column, float level){
        return new QuantileExact<>(column,level);
    }


}
