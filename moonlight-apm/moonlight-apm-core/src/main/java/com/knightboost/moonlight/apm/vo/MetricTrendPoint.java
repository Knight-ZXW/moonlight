package com.knightboost.moonlight.apm.vo;

import com.knightboost.moonlight.apm.service.MetricHumanizeService;
import com.knightboost.moonlight.apm.util.SpringBeanUtils;
import com.knightboost.moonlight.apm.util.chart.TrendUtil;
import lombok.Data;

import java.util.Date;

/*
 * Created by Knight-ZXW on 2022/7/24
 * email: nimdanoob@163.com
 */
@Data
public class MetricTrendPoint implements TrendItem{
    private long timestamp;
    private String readableDate;
    private Date date;
    private float value;
    private String category;
    private String readableCategory;

    @Override
    public Date getDate() {
        return date;
    }

    public static TrendUtil.MissingDataCreator<MetricTrendPoint> missingDateCreator() {
        return new TrendUtil.MissingDataCreator<MetricTrendPoint>() {
            @Override
            public MetricTrendPoint create(String groupName, long time) {
                MetricTrendPoint metricTrendPoint = new MetricTrendPoint();
                metricTrendPoint.setCategory(groupName);
                metricTrendPoint.setDate(new Date(time));

                metricTrendPoint.setReadableCategory(SpringBeanUtils.getBean(MetricHumanizeService.class)
                        .getAggregateMetricReadableName(groupName));
                metricTrendPoint.setTimestamp(time);
                metricTrendPoint.setValue(0);
                return metricTrendPoint;
            }
        };
    }
}
