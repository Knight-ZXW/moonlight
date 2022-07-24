package com.knightboost.moonlight.apm.dto;

import java.util.Date;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
public interface TrendItem {
    public Date getDate();

    public long getTimestamp();

    public void setReadableDate(String readableTime);
}
