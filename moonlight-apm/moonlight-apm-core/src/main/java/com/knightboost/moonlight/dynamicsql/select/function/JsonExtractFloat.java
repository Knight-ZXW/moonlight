package com.knightboost.moonlight.dynamicsql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction;

import java.sql.JDBCType;
import java.util.Optional;


/*
 * Created by Knight-ZXW on 2022/4/14
 * email: nimdanoob@163.com
 */
public class JsonExtractFloat<T> extends AbstractUniTypeFunction<T, JsonExtractFloat<T>> {
    private final String jsonFieldName;

    protected JsonExtractFloat(BindableColumn<T> column, String jsonFieldName) {
        super(column);
        this.jsonFieldName = jsonFieldName;
    }

    @Override
    protected JsonExtractFloat<T> copy() {
        return new JsonExtractFloat<>(column, jsonFieldName);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return String.format("JSONExtractFloat(%s,'%s')", column.renderWithTableAlias(tableAliasCalculator)
                , jsonFieldName);
    }

    @Override
    public Optional<JDBCType> jdbcType() {
        return Optional.of(JDBCType.NUMERIC);
    }

    public static <T> JsonExtractFloat<T> of(BindableColumn<T> column, String jsonFieldName){
        return new JsonExtractFloat<>(column,jsonFieldName);
    }
}
