package com.knightboost.moonlight.apm.core.query;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
@Data
public class BaseApmReq {
    private String appKey;
    private String issueType;
    private long beginTime;
    private long endTime;

    //具体查询的指标
    @JsonAlias({"metricQueries","metricAggregateQueries","metricQuery"})
    protected List<MetricAggregateQuery> metricAggregateQueries;

    public List<FilterRule> filterRules =new ArrayList<>();

    /**
     *
     * @param filedName
     * @return 返回某个字段过滤的第一条规则,如果未找到返回空
     */
    @Nullable
    public FilterRule getFilterRuleOfField(String filedName){
        for (FilterRule filterRule : filterRules) {
            if (filterRule.getField().equals(filedName)){
                return filterRule;
            }
        }
        return null;
    }


    public void addFilterRule(FilterRule filterRule){
        if (this.filterRules == null){
            this.filterRules = new ArrayList<>();
        }
        this.filterRules.add(filterRule);
    }

    public void addFilterRules(List<FilterRule> filterRules){
        if (this.filterRules == null){
            this.filterRules = new ArrayList<>();
        }
        if (filterRules!=null){
            this.filterRules.addAll(filterRules);
        }
    }

    //todo rename
    public FilterRule getFilterRule(String... filterNames) {
        if (this.filterRules == null || this.filterRules.size() == 0) {
            return null;
        }

        for (String filterName : filterNames) {
            for (FilterRule filterRule : filterRules) {
                if (filterRule.getField().equals(filterName)) {
                    return filterRule;
                }
            }
        }
        return null;
    }


    public List<FilterRule> getFilterRules(List<String> filedNames){
        ArrayList<FilterRule> rules = new ArrayList<>();
        for (FilterRule filterRule : filterRules) {
            if (filedNames.contains(filterRule)){
                rules.add(filterRule);
            }
        }
        return rules;
    }


}
