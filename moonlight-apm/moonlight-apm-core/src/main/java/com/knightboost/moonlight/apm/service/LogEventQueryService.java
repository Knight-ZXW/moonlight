package com.knightboost.moonlight.apm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knightboost.moonlight.apm.chdao.ApmLogEventMapper;
import com.knightboost.moonlight.apm.core.query.BaseApmReq;
import com.knightboost.moonlight.apm.core.query.LogEventQueryHelper;
import com.knightboost.moonlight.apm.core.query.PagingLogEventReq;
import com.knightboost.moonlight.apm.core.query.TimeAggergateType;
import com.knightboost.moonlight.apm.dao.ApmLogEventDSS;
import com.knightboost.moonlight.apm.dto.MetricTrendResultPTO;
import com.knightboost.moonlight.apm.entity.LogEvent;
import com.knightboost.moonlight.apm.util.HumanizeUtil;
import com.knightboost.moonlight.apm.util.chart.TrendUtil;
import com.knightboost.moonlight.apm.vo.MetricTrendPoint;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.mybatis.dynamic.sql.select.SelectDSL.select;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Service
public class LogEventQueryService extends ServiceImpl<ApmLogEventMapper, LogEvent> {


    @Autowired
    LogEventQueryHelper queryHelper;

    @Autowired
    MetricHumanizeService metricHumanizeService;


    public void insertLog(LogEvent logEvent) {
        getBaseMapper().insert(logEvent);
    }

    /**
     * 分页查询日志列表
     * @param req
     * @return
     */
    public PageInfo<LogEvent> pagingQueryApmLogEvent(PagingLogEventReq req) {
        Collection<BasicColumn> basicColumns = ApmLogEventDSS.logEvent.getColumnsOfGroup("basic");

        for (String detailField : req.getDetailFields()) {
            SqlColumn<Object> column = ApmLogEventDSS.logEvent.columnOf(detailField);
            if (column != null) {
                basicColumns.add(column);
            }
        }

        QueryExpressionDSL<SelectModel> queryDsl = select(basicColumns)
                .from(ApmLogEventDSS.logEvent);

        queryHelper.filterConditionBuild(queryDsl.where(),
                req);

        List<SortSpecification> sortSpecifications = queryHelper.orderConditionBuild(req.getSortRules());
        SelectDSL<SelectModel> selectDsl = null;

        //TODO 支持多排序规则
        if (sortSpecifications.size() != 0) {
            selectDsl = queryDsl.orderBy(sortSpecifications);
        } else {
            selectDsl = queryDsl.orderBy(SimpleSortSpecification.of(ApmLogEventDSS.logEvent.timestamp.name())
                    .descending());
        }
        SelectDSL<SelectModel> finalSelectDsl = selectDsl;

        //分页查询
        PageInfo<LogEvent> pageInfo = PageHelper.startPage(req.getPageNo(), req.getPageSize())
                .doSelectPageInfo(() -> {
                    ApmLogEventMapper apmLogEventMapper = getBaseMapper();
                    apmLogEventMapper.dynamicSelectEvents(finalSelectDsl
                            .build()
                            .render(RenderingStrategies.MYBATIS3));
                });

        return pageInfo;
    }


    /**
     * 根据时间聚合查询
     *
     * @param req
     * @return
     */
    public List<MetricTrendPoint> metricAggregateByTime(BaseApmReq req){
        //todo 条件对象化
        BasicColumn dateColumn = ApmLogEventDSS.aggregateEventTime(req.getTimeAggregationType(),
                "datetime");
        List<BasicColumn> selectColumns = queryHelper.interpretMetricQueryColumn(req.getMetricAggregateQueries());
        selectColumns.add(dateColumn);
        QueryExpressionDSL.FromGatherer<SelectModel> queryDsl = SelectDSL.select(selectColumns);

        QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder whereDsl = queryDsl
                .from(ApmLogEventDSS.logEvent)
                .where();
        queryHelper.filterConditionBuild(whereDsl,req);

        //根据日期排序
        SelectStatementProvider provider = whereDsl.groupBy(dateColumn)
                .orderBy(SimpleSortSpecification.of(dateColumn.alias().get()))
                .build().render(RenderingStrategies.MYBATIS3);

        List<MetricTrendResultPTO> metricTrendResultPTOS = getBaseMapper().queryAggregateMetricTrend(provider);

        //根据不同 metric 拆分成多个组
        ArrayList<MetricTrendPoint> pointTrendList = new ArrayList<>();

        for (MetricTrendResultPTO metricTrendResultPTO : metricTrendResultPTOS) {
            Date date = (Date) metricTrendResultPTO.get("datetime");
            for (Map.Entry<String, Object> entry : metricTrendResultPTO.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("aggregate")) {
                    String shortName = key.replace("aggregate_", "");

                    MetricTrendPoint metricTrendPoint = new MetricTrendPoint();
                    metricTrendPoint.setTimestamp(date.getTime());
                    metricTrendPoint.setDate(date);
                    metricTrendPoint.setReadableDate(HumanizeUtil
                            .truncDateAsString(date,
                                    TimeAggergateType.getReadableTimeTruncType(req.getTimeAggregationType())));
                    metricTrendPoint.setValue(((Number) entry.getValue()).floatValue());
                    metricTrendPoint.setCategory(shortName);
                    metricTrendPoint.setReadableCategory(metricHumanizeService
                            .getAggregateMetricReadableName(key));
                    pointTrendList.add(metricTrendPoint);
                }
            }
        }

        List<MetricTrendPoint> r = TrendUtil.makeDataContinuously(
                req.getTimeAggregationType(),
                req.getBeginTime(),
                req.getEndTime(), pointTrendList, MetricTrendPoint::getCategory,
                MetricTrendPoint.missingDateCreator());

        r.sort(Comparator.comparing(MetricTrendPoint::getDate));

        return r;

    }

}
