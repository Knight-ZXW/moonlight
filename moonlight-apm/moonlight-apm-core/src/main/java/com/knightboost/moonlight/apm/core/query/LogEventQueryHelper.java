package com.knightboost.moonlight.apm.core.query;

import com.knightboost.moonlight.apm.chdao.ApmLogEventMapper;
import com.knightboost.moonlight.apm.dao.ApmLogEventDSS;
import com.knightboost.moonlight.clickhouse.CKAggregateFunctionName;
import com.knightboost.moonlight.clickhouse.PRECISION;
import com.knightboost.moonlight.dynamicsql.select.aggregate.Quantile;
import com.knightboost.moonlight.dynamicsql.select.aggregate.QuantileExact;
import com.knightboost.moonlight.dynamicsql.select.function.CountIf;
import com.knightboost.moonlight.dynamicsql.select.function.IfBetween;
import com.knightboost.moonlight.dynamicsql.select.function.JsonExtractFloat;
import com.knightboost.moonlight.dynamicsql.select.function.ToNumber;
import com.knightboost.moonlight.dynamicsql.where.condition.IsGreaterThanOrEqualToDateTime;
import com.knightboost.moonlight.dynamicsql.where.condition.IsLessThanToDateTime;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.aggregate.Avg;
import org.mybatis.dynamic.sql.select.aggregate.Count;
import org.mybatis.dynamic.sql.select.aggregate.CountDistinct;
import org.mybatis.dynamic.sql.where.AbstractWhereDSL;
import org.mybatis.dynamic.sql.where.condition.IsEqualTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangyh.basic.utils.CamelCaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Slf4j
@Service
public class LogEventQueryHelper {

    @Autowired
    ApmLogEventMapper apmLogEventMapper;

    public void filterConditionBuild(AbstractWhereDSL selectDsl,
                                     BaseApmReq req) {
        selectDsl.and(ApmLogEventDSS.appKey, IsEqualTo.of(req.getAppKey()));
        ;

//        if (req.getBeginTime() > 0) {
//            selectDsl.and(ApmLogEventDSS.eventTime,
//                    IsGreaterThanOrEqualToDateTime.of(DateFormatUtils.format(req.getBeginTime(),"yyyy-MM-dd HH:mm:ss")));
//        }
//
//        if (req.getEndTime() > 0) {
//            selectDsl.and(ApmLogEventDSS.eventTime, IsLessThanToDateTime.of(
//                    DateFormatUtils.format(req.getEndTime(),"yyyy-MM-dd HH:mm:ss")));
//        }

        //eventTime 只精确到秒
        if (req.getBeginTime() > 0) {
            selectDsl.and(ApmLogEventDSS.eventTime,
                    IsGreaterThanOrEqualToDateTime.of(req.getBeginTime()/1000));
        }

        if (req.getEndTime() > 0) {
            selectDsl.and(ApmLogEventDSS.eventTime, IsLessThanToDateTime.of(
                   req.getEndTime()/1000));
        }

        //todo 将group id转为 filter 条件?

//        if ((req.getGroupIds() != null && req.getGroupIds().size() > 0) || req.getGroupId() > 0) {
//            ArrayList<Long> groupIds = new ArrayList<>();
//
//            if (req.getGroupIds() != null && req.getGroupIds().size() > 0) {
//                groupIds.addAll(req.getGroupIds());
//            } else if (req.getGroupId() > 0) {
//                groupIds.add(req.getGroupId());
//            }
//
//            if (groupIds.size() > 0) {
//                FilterRule filterRule = new FilterRule();
//                filterRule.setFilterType(FilterRule.FILTER_TYPE_IN);
//                filterRule.setField(ApmLogEventDSS.logEvent.groupId.name());
//                filterRule.setFilterValue(groupIds);
//                req.getFilterRules().add(filterRule);
//            }
//        }


        if (req.getFilterRules() != null) {
            for (FilterRule filterRule : req.filterRules) {
                String field = filterRule.getField();
                BindableColumn column = ApmLogEventDSS.logEvent.columnOf(field);
                if (column == null) {
                    if (field.startsWith("tag_")) {
                        field = field.replace("tag_", "");
                        column = ApmLogEventDSS.columnOfTag(field);
                    }

                    if (field.startsWith("metrics.")) {
                        field = field.replace("metrics.", "");
                        column = ApmLogEventDSS.logEvent.metricValueColumn(field);
                        column = column.as(field);
                    }

                }
                if (column != null) {
                    VisitableCondition condition = filterRule.visitableCondition(column);
                    if (condition != null) {
                        selectDsl.and(column, condition);
                    }
                }
            }
        }
    }

    public List<SortSpecification> orderConditionBuild(List<SortRule> sortRules) {
        ArrayList<SortSpecification> sorts = new ArrayList<>();
        for (SortRule sortRule : sortRules) {
            String sortField = sortRule.getSortField();

            if (sortField.startsWith("metrics.")) {
                BasicColumn column = ApmLogEventDSS.logEvent.metricValueColumn(sortField);

                SortSpecification sortSpecification = SimpleSortSpecification.of(column.renderWithTableAlias(TableAliasCalculator.empty()));
                if (sortRule.isDescending()) {
                    sortSpecification = sortSpecification.descending();
                }
                sorts.add(sortSpecification);
                continue;
            }

            SqlColumn<Object> sqlColumn = ApmLogEventDSS.logEvent.columnOf(sortField);
            if (sqlColumn == null) {
                continue;
            }
            if (sortRule.isDescending()) {
                sorts.add(sqlColumn.descending());
            } else {
                sorts.add(sqlColumn);
            }
        }
        return sorts;
    }

    public BasicColumn interpretMetricAggregateQuery(MetricAggregateQuery metricAggregateQuery) {
        String metricName = metricAggregateQuery.getMetricName();
        BasicColumn column = null;

        //兜底翻译逻辑
        if (column == null) {
            //是否是已知的保留字段，如 deviceCount
            String name = CamelCaseUtil.camelCaseToUnderLine(metricName);
            column = ApmLogEventDSS.logEvent.columnOf(name);

            //其他情况默认为 读取 metrics
            //todo 支持tag_  ?
            if (column == null) {
                column = JsonExtractFloat.of(ApmLogEventDSS.logEvent.metric, metricName);
            }
        }
        String aggregateFunction = metricAggregateQuery.getAggregateFunction();
        switch (aggregateFunction) {
            case CKAggregateFunctionName.AVG:
                column = ToNumber.of(Avg.of((BindableColumn) column), PRECISION.Decimal64)
                        .as("aggregate_" + CKAggregateFunctionName.AVG + "__"
                                + metricAggregateQuery.getMetricName());
                break;
            case CKAggregateFunctionName.QUANTILE:
                float quantilePercent = ((Number) metricAggregateQuery.getParams()
                        .get("percent")).floatValue();

                String percent = Float.valueOf(quantilePercent).toString()
                        .replace(".", "d");

                column = ToNumber.of(Quantile.of((BindableColumn) column, quantilePercent),
                                PRECISION.Decimal64)
                        .as("aggregate_" + CKAggregateFunctionName.QUANTILE + percent + "__"
                                + metricAggregateQuery.getMetricName());
                break;
            case CKAggregateFunctionName.QUANTILExact:
                float quantileExactPercent = ((Number) metricAggregateQuery.getParams()
                        .get("percent")).floatValue();

                String exactPercent = Float.valueOf(quantileExactPercent).toString()
                        .replace(".", "d");

                column = ToNumber.of(QuantileExact.of((BindableColumn) column, quantileExactPercent),
                                PRECISION.Decimal64)
                        .as("aggregate_" + CKAggregateFunctionName.QUANTILE + exactPercent + "__"
                                + metricAggregateQuery.getMetricName());
                break;
            case CKAggregateFunctionName.COUNT:
                column = Count.of(column)
                        .as("aggregate_" + CKAggregateFunctionName.COUNT + "__"
                                + metricAggregateQuery.getMetricName());
                break;
            case CKAggregateFunctionName.DISTINCT_COUNT:
                column = CountDistinct.of(column)
                        .as("aggregate_" + CKAggregateFunctionName.DISTINCT_COUNT + "__"
                                + metricAggregateQuery.getMetricName());
                break;
            case CKAggregateFunctionName.COUNT_RANGE:
                Map<String, Object> map = metricAggregateQuery.getParams();
                Object from = map.get("from");
                Object to = map.get("to");
                if (from != null && to != null) {
                    column = CountIf.rangeIn((BindableColumn) column, IfBetween.of(from, to));
                }
                break;
        }
        if (metricAggregateQuery.getAlias() != null && metricAggregateQuery.getAlias().length() > 0) {
            if (column != null) {
                column = column.as(metricAggregateQuery.getSqlAlias());
            }
        }
        return column;
    }

    public List<BasicColumn> interpretMetricQueryColumn(List<MetricAggregateQuery> metricAggregateQueries) {
        List<BasicColumn> aggregateQueryColumns = new ArrayList<>();

        if (metricAggregateQueries != null) {
            for (MetricAggregateQuery metricAggregateQuery : metricAggregateQueries) {
                BasicColumn column = interpretMetricAggregateQuery(metricAggregateQuery);
                if (column != null) {
                    aggregateQueryColumns.add(column);
                } else {
                    //todo throwException
                }
            }
        }
        return aggregateQueryColumns;

    }


}
