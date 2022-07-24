package com.knightboost.moonlight.apm.core.query;

import com.knightboost.moonlight.apm.util.HumanizeUtil;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public interface TimeAggergateType {
    public static final String ONE_DAY = "1day";
    public static final String ONE_HOUR = "1hour";
    public static final String ONE_MINUTE = "1minute";
    public static final String FIVE_MINUTE = "5minute";

    public static String getReadableTimeTruncType(String timeRange) {
        if (timeRange.contains("day")) {
            return HumanizeUtil.TRUNC_DAY;
        } else if (timeRange.contains("hour")) {
            return HumanizeUtil.TRUNC_HOUR;
        } else if (timeRange.contains("minute")) {
            return HumanizeUtil.TRUNC_HOUR;
        }
        return timeRange;
    }
}
