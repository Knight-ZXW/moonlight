package com.knightboost.moonlight.apm.service;

import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knightboost.moonlight.apm.chdao.ApmLogEventMapper;
import com.knightboost.moonlight.apm.core.query.BaseApmReq;
import com.knightboost.moonlight.apm.core.query.FilterRule;
import com.knightboost.moonlight.apm.core.query.LogEventQueryHelper;
import com.knightboost.moonlight.apm.core.query.SortRule;
import com.knightboost.moonlight.apm.core.result.GroupAggregateInfo;
import com.knightboost.moonlight.apm.core.result.QueryResultMapHelper;
import com.knightboost.moonlight.apm.dao.ApmIssueDSS;
import com.knightboost.moonlight.apm.dao.ApmIssueMapper;
import com.knightboost.moonlight.apm.dao.ApmLogEventDSS;
import com.knightboost.moonlight.apm.dto.IssueListReq;
import com.knightboost.moonlight.apm.dto.SqlResultEntity;
import com.knightboost.moonlight.apm.entity.ApmIssue;
import com.knightboost.moonlight.apm.util.paging.PageDto;
import com.knightboost.moonlight.apm.util.paging.PageDtoUtil;
import com.knightboost.moonlight.apm.vo.ApmIssueListItem;
import lombok.AllArgsConstructor;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.where.condition.IsEqualTo;
import org.mybatis.dynamic.sql.where.condition.IsNotEqualTo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mybatis.dynamic.sql.select.SelectDSL.select;


/*
 * TODO 改为专门表示提供给 平台 查询数据的Service
 *  Created by Knight-ZXW on 2022/7/17
 * email: nimdanoob@163.com
 */
@Service
@AllArgsConstructor
public class ApmIssueService extends ServiceImpl<ApmIssueMapper, ApmIssue> {


    private final UidGenerator uidGenerator;
    private final LogEventQueryHelper eventQueryHelper;

    private final ApmLogEventMapper eventMapper;

    /**
     * insert new issue
     * <p>
     * TODO  new issues notify
     *
     * @param apmIssue
     * @return
     */
    public Long insertIssue(ApmIssue apmIssue) {
        long id = uidGenerator.getUid();
        apmIssue.setId(id);
        getBaseMapper().insert(apmIssue);
        return id;
    }

    public PageImpl<ApmIssueListItem> pagingQueryIssues(IssueListReq req){
        List<ApmIssueListItem> apmIssues = queryIssuesList(req);
        PageDto<ApmIssueListItem> pageDto = PageDtoUtil.getPageDto(apmIssues, req.getPageNo(),
                req.getPageSize());

        PageImpl<ApmIssueListItem> page = new PageImpl<>(
                pageDto.contents,
                Pageable.unpaged(),
                apmIssues.size()
        );
        return  page;
    }


    /**
     * 根据条件 返回 Issue 列表
     *
     * @param req
     */
    public List<ApmIssueListItem> queryIssuesList(IssueListReq req) {
        //1. 先从Issue表中查询 符合基本条件的 issue ,降低在 ck中进行实时计算的数量
        ApmIssueMapper issueMapper = getBaseMapper();

        QueryExpressionDSL<SelectModel> queryDsl = SqlBuilder.select(ApmIssueDSS.apmIssue.allColumns())
                .from(ApmIssueDSS.apmIssue);

        QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder whereDsl = queryDsl.where();
        whereDsl =
                whereDsl.and(ApmIssueDSS.appKey, IsEqualTo.of(req.getAppKey())).
                        and(ApmIssueDSS.apmIssue.isDel, IsNotEqualTo.of(1))
                        .and(ApmIssueDSS.apmIssue.issueType, IsEqualTo.of(req.getIssueType()));

        FilterRule stateFilterRule = req.getFilterRuleOfField("state");
        //issue 状态过滤
        if (stateFilterRule != null) {
            whereDsl = whereDsl.and(ApmIssueDSS.apmIssue.state, stateFilterRule.visitableCondition(ApmIssueDSS.apmIssue.state));
        }

        List<ApmIssue> apmIssues = issueMapper.selectIssues(whereDsl.build().render(RenderingStrategies.MYBATIS3));

        //
        List<ApmIssueListItem> apmIssueVOList = new ArrayList<ApmIssueListItem>();
        for (ApmIssue apmIssue : apmIssues) {
            ApmIssueListItem vo = new ApmIssueListItem();
            BeanUtils.copyProperties(apmIssue, vo);
            apmIssueVOList.add(vo);
        }

        List<Long> groupIdSet = apmIssueVOList.stream()
                .map(ApmIssueListItem::getId)
                .collect(Collectors.toList());
        //todo 时间范围控制

        BaseApmReq logGroupMetricReq = new BaseApmReq();
        logGroupMetricReq.setMetricAggregateQueries(req.getMetricAggregateQueries());

        logGroupMetricReq.setAppKey(req.getAppKey());
        logGroupMetricReq.setBeginTime(req.getBeginTime());
        logGroupMetricReq.setEndTime(req.getEndTime());

        //todo issueType 和 eventType的关系？

        logGroupMetricReq.addFilterRules(req.getFilterRules());
        logGroupMetricReq.addFilterRule(FilterRule.createRule(ApmLogEventDSS
                .logEvent.groupId.name(), FilterRule.FILTER_TYPE_IN, groupIdSet));

        //TODO  sql 层面直接支持order by

        List<GroupAggregateInfo> groupAggregateInfos = issueMetricAggregate(logGroupMetricReq);

        Map<Long, GroupAggregateInfo> groupCountMap = groupAggregateInfos.stream()
                .collect(Collectors.toMap(GroupAggregateInfo::getGroupId,
                        groupAggregateInfo -> groupAggregateInfo));

        if (groupCountMap.size() == 0) {
            return Collections.emptyList();
        }

        Stream<ApmIssueListItem> stream = apmIssueVOList.stream().filter(apmIssue -> {
            GroupAggregateInfo groupAggregateInfo = groupCountMap.get(apmIssue.getGroupId());
            if (groupAggregateInfo != null) {

                Map<String, Number> metrics = groupAggregateInfo.getMetrics();
                apmIssue.setMetrics(metrics);
                if (apmIssue.getCount() == 0 && metrics.get("count_device_uuid") != null) {
                    apmIssue.setCount(metrics.get("count_device_uuid").longValue());
                } else if (apmIssue.getDeviceCount() == 0 && metrics.get("distinctCount_device_uuid") != null) {
                    apmIssue.setDeviceCount(metrics.get("count_device_uuid").longValue());
                }
            } else {
                apmIssue.setCount(0L);
                apmIssue.setDeviceCount(0L);
            }
            return true;
        }).filter(apmIssue -> apmIssue.getCount() > 0 || apmIssue.getMetrics().size() > 0);



        List<SortRule> sortRules = req.getSortRules();
        stream = sortPagingList(stream, sortRules);
        //查询采样率信息

        return stream.collect(Collectors.toList());
    }

    private Stream<ApmIssueListItem> sortPagingList(Stream<ApmIssueListItem> stream, List<SortRule> sortRules) {
        if (sortRules == null || sortRules.size() == 0) { // 默认实现逻辑
            Comparator<ApmIssueListItem> comparator = Comparator.nullsLast(
                    Comparator.comparingLong(ApmIssueListItem::getCount)).reversed();
            stream = stream.sorted(comparator);
            return stream;
        }

        Comparator<ApmIssueListItem> comparator = null;

        for (SortRule sortRule : sortRules) {
            Comparator<ApmIssueListItem> next = getComparator(sortRule);
            if (comparator == null) {
                comparator = next;
            } else {
                comparator.thenComparing(next);
            }
        }
        if (comparator != null) {
            return stream.sorted(comparator);
        }
        return stream;
    }


    private Comparator<ApmIssueListItem> getComparator(SortRule sortRule) {
        String sortField = sortRule.getSortField();
        Comparator<ApmIssueListItem> comparator = null;

        if ("count".equals(sortField)) {
            comparator = Comparator.comparingLong(ApmIssueListItem::getCount);
        } else if ("firstSeen".equals(sortField)) {
            comparator = Comparator.comparing(ApmIssueListItem::getFirstSeen);
        } else if (sortField.startsWith("metrics")) {
            String fieldName = sortField.substring("metrics.".length());
            //其他情况认为是metric排序
            comparator = new Comparator<ApmIssueListItem>() {
                @Override
                public int compare(ApmIssueListItem o1, ApmIssueListItem o2) {
                    try {
                        return (int) (o1.getMetrics().get(fieldName).doubleValue()
                                - o2.getMetrics().get(fieldName).doubleValue());
                    } catch (Exception e) {
                        return 0;
                    }

                }
            };
        }
        if (sortRule.isDescending()) {
            return comparator.reversed();
        }

        return comparator;
    }


    /**
     * 根据条件查询 相关 Issue的 指标信息
     *
     * @param req
     * @return
     */
    public List<GroupAggregateInfo> issueMetricAggregate(BaseApmReq req) {
        List<SqlResultEntity> groupList = queryIssuesAggregateResult(req);
        return QueryResultMapHelper.convertMetricMapToEntity(groupList);
    }


    public List<SqlResultEntity> queryIssuesAggregateResult(BaseApmReq req) {
        SelectStatementProvider statementProvider = issueAggregateStatement(req);
        return eventMapper.selectIssuesMetric(statementProvider);
    }

    /**
     * 查询Issue的多个相关聚合新息
     *
     * @param req
     */
    public SelectStatementProvider issueAggregateStatement(BaseApmReq req) {
        List<BasicColumn> selectColumns = eventQueryHelper.interpretMetricQueryColumn(req.getMetricAggregateQueries());
        selectColumns.add(0, ApmLogEventDSS.groupId.asCamelCase());
        QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder whereBuilder = select(selectColumns).from(ApmLogEventDSS.logEvent).where();
        eventQueryHelper.filterConditionBuild(whereBuilder, req);

        SelectStatementProvider statementProvider = whereBuilder.groupBy(ApmLogEventDSS.groupId)
                .build().render(RenderingStrategies.MYBATIS3);

        return statementProvider;


    }

}
