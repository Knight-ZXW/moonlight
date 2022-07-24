package com.knightboost.moonlight.apm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knightboost.moonlight.apm.chdao.ApmLogEventMapper;
import com.knightboost.moonlight.apm.core.query.LogEventQueryHelper;
import com.knightboost.moonlight.apm.core.query.PagingLogEventReq;
import com.knightboost.moonlight.apm.dao.ApmLogEventDSS;
import com.knightboost.moonlight.apm.entity.LogEvent;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static org.mybatis.dynamic.sql.select.SelectDSL.select;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Service
public class LogEventQueryService extends ServiceImpl<ApmLogEventMapper, LogEvent> {


    @Autowired
    LogEventQueryHelper queryHelper;

    public void insertLog(LogEvent logEvent) {
        getBaseMapper().insert(logEvent);
    }

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


}
