package com.knightboost.moonlight.apm.core.query;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.VisitableCondition;
import org.mybatis.dynamic.sql.where.condition.*;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
public class FilterRule {
    /**
     * 过滤规则的字段
     */
    @JsonAlias({"dimension", "field", "name"})
    private String field;
    /**
     * 过滤操作类型
     */
    @JsonAlias({"filterType", "op","opt"})
    private String filterType;

    /**
     * 筛选值
     */
    @JsonAlias({"filterValue", "values", "value"})
    private Object filterValue;

    //
    public static final String FILTER_TYPE_IN = "in";
    public static final String FILTER_TYPE_GT = ">";
    public static final String FILTER_TYPE_GTE = ">=";
    public static final String FILTER_TYPE_LT = "<";
    public static final String FILTER_TYPE_LTE = "<=";
    public static final String FILTER_TYPE_LIKE = "like";
    public static final String FILTER_TYPE_BETWEEN = "between";
    public static final String FILTER_TYPE_EQ = "=";
    public static final String FILTER_TYPE_NEQ = "!=";
    public static final String FILTER_TYPE_EQ_OLD = "eq";
    public static final String FILTER_TYPE_N_EQ = "neq";


    public static FilterRule createRule(String field,
                                        String filterType, Object filterValue) {
        FilterRule filterRule = new FilterRule();
        filterRule.setField(field);
        filterRule.setFilterType(filterType);
        filterRule.setFilterValue(filterValue);
        return filterRule;
    }


    public String getStringFilterValue() {
        return (String) filterValue;
    }

    public List<String> getStringListFilterValue() {
        return (List<String>) filterValue;
    }

    public List<Integer> getIntegerListFilterValue() {
        return (List<Integer>) filterValue;
    }

    public Boolean getBooleanValue() {
        if (filterValue == null) {
            return null;
        }
        return (Boolean) filterValue;
    }

    public Long getLongValue() {
        if (filterValue == null) {
            return null;
        }
        return ((Number) filterValue).longValue();
    }



    /**
     * 规则转换成条件
     *
     * @param sqlColumn
     */
    public <T> VisitableCondition<T> visitableCondition(BasicColumn sqlColumn) {

        Optional jdbcTypeOptional = Optional.empty();

        if (sqlColumn instanceof SqlColumn) {
            jdbcTypeOptional = ((SqlColumn<?>) sqlColumn).jdbcType();
        }

        if (jdbcTypeOptional.isPresent()) {
            JDBCType jdbcType = (JDBCType) jdbcTypeOptional.get();
            if (jdbcType.equals(JDBCType.TIME) || jdbcType.equals(JDBCType.TIMESTAMP)) {
                if (filterValue instanceof Number) {
                    filterValue = new Date(((Number) filterValue).longValue());
                } else if (filterValue instanceof List) {
                    List list = (List) this.filterValue;
                    Object v = list.get(0);
                    if (v instanceof Number) {
                        ArrayList<Date> convertedValues = new ArrayList<>();
                        for (Object o : list) {
                            convertedValues.add(new Date(((Number) o).longValue()));
                        }
                        list.clear();
                        list.addAll(convertedValues);
                    }
                }
            }
        }

        if (FILTER_TYPE_EQ.equals(filterType) || FILTER_TYPE_EQ_OLD.equals(filterType)) {
            return (VisitableCondition<T>) SqlBuilder.isEqualToWhenPresent(filterValue);
        } else if (FILTER_TYPE_NEQ.equals(filterType) || FILTER_TYPE_N_EQ.equals(filterType)) {
            return (VisitableCondition<T>) SqlBuilder.isNotEqualToWhenPresent(filterValue);
        } else if (FILTER_TYPE_IN.equals(filterType)) {
            if (filterValue instanceof List) {
                return (VisitableCondition<T>) IsIn.of(((List<?>) filterValue));
            } else {
                return (VisitableCondition<T>) IsIn.of(filterValue);
            }
        } else if (FILTER_TYPE_GTE.equals(filterType)) {
            return (VisitableCondition<T>) IsGreaterThanOrEqualTo.of(filterValue);
        } else if (FILTER_TYPE_GT.equals(filterType)) {
            return (VisitableCondition<T>) IsGreaterThan.of(filterValue);
        }
        if (FILTER_TYPE_LT.equals(filterType)) {
            return (VisitableCondition<T>) IsLessThan.of(filterValue);
        }
        if (FILTER_TYPE_LTE.equals(filterType)) {
            return (VisitableCondition<T>) IsLessThanOrEqualTo.of(filterValue);
        }
        if (FILTER_TYPE_LIKE.equals(filterType)) {
            if (!((String) filterValue).contains("%")) {
                return (VisitableCondition<T>) IsLike.of("%" + filterValue + "%");
            } else {
                return (VisitableCondition<T>) IsLike.of(filterValue);
            }
        } else if (FILTER_TYPE_BETWEEN.equals(filterType)) {
            ArrayList<Number> values = (ArrayList<Number>) this.filterValue;
            return (VisitableCondition<T>) IsBetween.isBetween(((List) this.filterValue).get(0))
                    .and(((List) this.filterValue).get(1));
        }
        throw  new IllegalArgumentException("unexpect filter condition");
    }
}
