package com.knightboost.moonlight.dynamicsql.select.function;

import com.knightboost.moonlight.clickhouse.PRECISION;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class ToNumber<T> extends AbstractUniTypeFunction<T, ToNumber<T>> {

    public static final String PRECISION_64 = "toDecimal64";
    public static final String FLOAT_64 = "toFloat64";

    private PRECISION precision;
    private int scale = 2;

    protected ToNumber(BindableColumn<T> column,
                       PRECISION precision) {
        super(column);
        this.precision = precision;
    }

    protected ToNumber(BindableColumn<T> column,
                       PRECISION precision, int scale) {
        super(column);
        this.precision = precision;
        this.scale = scale;
    }

    @Override
    protected ToNumber<T> copy() {
        return new ToNumber<>(column, precision, scale);
    }


    //        return this.setName("toDecimal64(" + name + ", " + calculatedPrecision + ")");
    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {

        if (precision.functionPrefix.startsWith("toDecimal")) {
            return this.precision.functionPrefix + "(" + column.renderWithTableAlias(tableAliasCalculator) + "," + scale + ")";
        } else {
            return this.precision.functionPrefix + "(" + column.renderWithTableAlias(tableAliasCalculator) + ")";
        }
    }

    public static <T> ToNumber<T> of(BindableColumn<T> column, PRECISION precision) {
        return new ToNumber<>(column, precision);
    }

    public static <T> ToNumber<T> of(BindableColumn<T> column, PRECISION precision, int scale) {
        return new ToNumber<>(column, precision, scale);
    }
}
