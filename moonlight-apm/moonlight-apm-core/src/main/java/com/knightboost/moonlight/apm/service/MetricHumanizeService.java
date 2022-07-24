package com.knightboost.moonlight.apm.service;

import com.knightboost.moonlight.clickhouse.CKAggregateFunctionName;
import org.springframework.stereotype.Service;

/*
 * Created by Knight-ZXW on 2022/7/24
 * email: nimdanoob@163.com
 */
@Service
public class MetricHumanizeService {


    public String getAggregateMetricOriginalName(String aggregateMetricName){
        String metricName = aggregateMetricName
                .substring(aggregateMetricName.lastIndexOf("__")+2,aggregateMetricName.length());
        return metricName;
    }

    public String getAggregateMetricReadableName(String aggregateMetricName){
        //todo
        String shortName = aggregateMetricName.replace("aggregate_", "");

        String quantileDesc = null;
        if (shortName.contains(CKAggregateFunctionName.AVG)){
            quantileDesc = "平均值";
        } else if (shortName.startsWith("count_")){
            quantileDesc = "数量";
        }else if (shortName.contains("quantile")){ //quantile
            float percent = Float.valueOf(shortName.substring(shortName.indexOf("0d"),shortName.indexOf("__"))
                    .replace("d","."));
            quantileDesc = percent*100+"分位";
        }

        if (quantileDesc == null){
            return aggregateMetricName;
        }

        String metricName = getAggregateMetricOriginalName(aggregateMetricName);

        return getReadableMetricName(metricName)+" "+quantileDesc;
    }

    public String getReadableMetricName(String metricName){
        switch (metricName){
            case "pageStartup":
                return "页面启动耗时";
            case "userExperiencePageStartup":
                return "页面体验启动耗时";
            case "slowFramesRate":
                return "掉帧率";
            case "uuid":
                return "设备数";
            default:
                return metricName;
        }
    }
}