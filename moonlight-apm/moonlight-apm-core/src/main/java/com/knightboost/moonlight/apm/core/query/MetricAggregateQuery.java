package com.knightboost.moonlight.apm.core.query;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
public class MetricAggregateQuery {
    private String eventName;
    private String metricName;
    private String aggregateFunction;
    private Map<String,Object> params = new LinkedHashMap<>();
    private String alias;

    //todo support groupBy


    public void addParam(String k,Object v){
        params.put(k,v);
    }

    public MetricAggregateQuery(){

    }
    public String getSqlAlias(){
        if (alias ==null ){
            return null;
        }
        return "`"+alias+"`";
    }

    public MetricAggregateQuery(String metricName,String aggregateFunction){
        this.metricName = metricName;
        this.aggregateFunction = aggregateFunction;
    }
}
