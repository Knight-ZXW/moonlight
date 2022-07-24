package com.knightboost.moonlight.apm.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
public class MetricTrendResultPTO extends HashMap<String, Object>
        implements TrendItem {

    @Override
    public Date getDate() {
        return (Date) get("datetime");
    }

    public void setDate(Date dateTime) {
        put("datetime", dateTime);
    }


    @JsonGetter
    @Override
    public long getTimestamp() {
        return getDate().getTime();
    }

    @Override
    public void setReadableDate(String readableTime) {
        put("readableTime", readableTime);
    }

    public String getReadableDate() {
        return (String) get("readableTime");
    }
}

