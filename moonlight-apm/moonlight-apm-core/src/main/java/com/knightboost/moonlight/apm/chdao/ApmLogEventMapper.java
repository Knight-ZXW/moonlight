package com.knightboost.moonlight.apm.chdao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knightboost.moonlight.apm.dto.DistributionItem;
import com.knightboost.moonlight.apm.dto.MetricTrendResultPTO;
import com.knightboost.moonlight.apm.dto.SqlResultEntity;
import com.knightboost.moonlight.apm.entity.LogEvent;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/7/20
 * email: nimdanoob@163.com
 */
@Component
public interface ApmLogEventMapper extends BaseMapper<LogEvent> {


    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<LogEvent> dynamicSelectEvents(SelectStatementProvider statementProvider);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<SqlResultEntity> selectIssuesMetric(SelectStatementProvider statementProvider);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<DistributionItem> selectDimensionValues(SelectStatementProvider statementProvider);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<List<String>> selectTags(SelectStatementProvider statementProvider);


    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<MetricTrendResultPTO> selectLineChart(SelectStatementProvider statementProvider);

}
