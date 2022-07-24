package com.knightboost.moonlight.apm.util.chart;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.knightboost.moonlight.apm.core.query.TimeAggergateType;
import com.knightboost.moonlight.apm.util.HumanizeUtil;
import com.knightboost.moonlight.apm.vo.TrendItem;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Created by Knight-ZXW on 2022/7/24
 * email: nimdanoob@163.com
 */
public class TrendUtil {


    public interface MissingDataCreator<T> {
        T create(String groupName, long time);
    }

    public static final long ONE_HOUR_MILL_SECONDS = 60 * 60 * 1000;
    public static final long ONE_DAY_MILL_SECONDS = 24 * ONE_HOUR_MILL_SECONDS;

    public static <T extends TrendItem> List<T> makeDataContinuously(String aggregateTimeRange,
                                                                     long beginTime,
                                                                     long endTime,
                                                                     List<T> items,
                                                                     Function<T, String> groupingFunc
            , MissingDataCreator<T> creator) {
        DateField dateField = null;
        long timeRange = ONE_DAY_MILL_SECONDS;
        if (aggregateTimeRange.equals(TimeAggergateType.ONE_DAY)) {
            dateField = DateField.DAY_OF_YEAR;
            timeRange = ONE_DAY_MILL_SECONDS;

        } else {
            dateField = DateField.HOUR_OF_DAY;
            timeRange = ONE_HOUR_MILL_SECONDS;
        }
        beginTime = DateUtil.truncate(new Date(beginTime), dateField).getTime();
        endTime = DateUtil.truncate(new Date(endTime), dateField).getTime();
        ArrayList<T> result = new ArrayList<>();
        long finalBeginTime = beginTime;
        long finalEndTime = endTime;
        long finalTimeRange = timeRange;
        DateField finalDateField = dateField;

        Map<String, List<T>> buildGroup =
                items.stream()
                        .filter(new Predicate<T>() {
                            @Override
                            public boolean test(T t) {
                                t.setReadableDate(HumanizeUtil
                                        .truncDateAsString(new Date(t.getTimestamp()),
                                                finalDateField));
                                return true;
                            }
                        })
                        .collect(Collectors.groupingBy(groupingFunc));

        buildGroup.forEach((group, list) -> {

            for (long time = finalBeginTime, index = 0;
                 time <= finalEndTime; time += finalTimeRange, index++) {

                T find = findItemByTime(list, time);
                if (find == null) {
                    //不存在 则补充
                    T item = creator.create(group, time);
                    item.setReadableDate(HumanizeUtil
                            .truncDateAsString(new Date(item.getTimestamp()),
                                    finalDateField));
                    result.add((int) index, item);
                } else {
                    find.setReadableDate(HumanizeUtil
                            .truncDateAsString(new Date(find.getDate().getTime()),
                                    finalDateField));
                    result.add(find);
                }
            }

        });
        result.sort(Comparator.comparing(T::getDate));
        return result;

    }

    public static <T extends TrendItem> T findItemByTime(List<T> items,
                                                         long time) {
        return findFirstByTime(items, time, true);
    }

    public static <T extends TrendItem> T findFirstByTime(List<T> items,
                                                          long time, boolean onlyOneCheck) {
        List<T> r = filterByTime(items, time);
        if (r.size() == 0)
            return null;

        if (onlyOneCheck) {
            if (r.size() > 1) {
                throw new IllegalStateException("find more than one");
            }
        }
        return r.get(0);
    }

    public static <T extends TrendItem> List<T> filterByTime(List<T> items, long time) {
        if (items == null)
            return null;
        return items.stream().filter(eventCountTrendItem -> eventCountTrendItem.getDate().getTime() == time)
                .collect(Collectors.toList());
    }
}
