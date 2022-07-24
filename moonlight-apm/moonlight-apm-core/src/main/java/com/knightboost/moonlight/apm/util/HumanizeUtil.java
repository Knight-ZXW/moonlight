package com.knightboost.moonlight.apm.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.knightboost.moonlight.apm.core.query.TimeAggergateType;

import java.util.Calendar;
import java.util.Date;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public class HumanizeUtil {

    public static final String TRUNC_DAY = "day";
    public static final String TRUNC_HOUR = "hour";
    public static final String TRUNC_MINUTE = "minute";

    private static final String TRUNC_DAY_FORMAT = "yyyy-MM-dd";
    private static final String TRUNC_HOUR_FORMAT = "yyyy-MM-dd HH";
    private static final String TRUNC_MINUTE_FORMAT = "yyyy-MM-d HH:mm";

    public static String truncDateAsString(Date date, String truncType) {
        switch (truncType) {
            case TRUNC_DAY:
            case TimeAggergateType.ONE_DAY:
                return DateUtil.format(date, TRUNC_DAY_FORMAT);
            case TRUNC_HOUR:
            case TimeAggergateType.ONE_HOUR:
                return DateUtil.format(date, TRUNC_HOUR_FORMAT);
            case TRUNC_MINUTE:
            case TimeAggergateType.ONE_MINUTE:
                return DateUtil.format(date, TRUNC_MINUTE_FORMAT);
        }

        return DateUtil.format(date, TRUNC_MINUTE_FORMAT);
    }

    public  static String truncDateAsString(Date date, DateField dateField) {
        switch (dateField.getValue()) {
            case Calendar.DAY_OF_YEAR:
                return DateUtil.format(date, TRUNC_DAY_FORMAT);
            case Calendar.HOUR_OF_DAY:
                return DateUtil.format(date, TRUNC_HOUR_FORMAT);
            case Calendar.MINUTE:
                return DateUtil.format(date, TRUNC_MINUTE_FORMAT);
        }
        return DateUtil.format(date, TRUNC_MINUTE_FORMAT);
    }
}
