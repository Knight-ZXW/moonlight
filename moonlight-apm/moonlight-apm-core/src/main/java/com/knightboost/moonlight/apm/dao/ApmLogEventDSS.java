package com.knightboost.moonlight.apm.dao;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.knightboost.moonlight.apm.core.query.TimeAggergateType;
import com.knightboost.moonlight.dynamicsql.ColumnGroup;
import com.knightboost.moonlight.dynamicsql.SqlTablePlus;
import com.knightboost.moonlight.dynamicsql.select.function.JsonExtractFloat;
import com.knightboost.moonlight.dynamicsql.select.function.ToStartOfDay;
import com.knightboost.moonlight.dynamicsql.select.function.ToStartOfHour;
import com.knightboost.moonlight.dynamicsql.select.function.ToStartOfMinute;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.util.Date;
import java.util.List;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public class ApmLogEventDSS {

    public static final LogEvent logEvent = new LogEvent();

    public static final SqlColumn<Integer> id = logEvent.eventId;
    public static final SqlColumn<String> appKey = logEvent.appKey;
    public static final SqlColumn<String> eventType = logEvent.eventType;
    public static final SqlColumn<Date> time = logEvent.time;
    public static final SqlColumn<Date> timestamp = logEvent.timestamp;
    public static final SqlColumn<String> appVersion = logEvent.version;
    public static final SqlColumn<String> deviceModel = logEvent.deviceModel;
    public static final SqlColumn<String> deviceName = logEvent.deviceName;
    public static final SqlColumn<String> deviceOs = logEvent.deviceOs;
    public static final SqlColumn<String> deviceOsVersion = logEvent.deviceOsVersion;
    public static final SqlColumn<String> deviceUUID = logEvent.deviceUUID;
    public static final SqlColumn<String> deviceBrand = logEvent.deviceBrand;
    public static final SqlColumn<String> userId = logEvent.userId;
    public static final SqlColumn<String> hash = logEvent.groupHash;
    public static final SqlColumn<String> sessionId = logEvent.sessionId;
    public static final SqlColumn<ObjectNode> extra = logEvent.extra;
    public static final SqlColumn<Long> groupId = logEvent.groupId;

    public final SqlColumn<List<String>> tagsKey = logEvent.tagsKey;
    public final SqlColumn<List<String>> tagsValue = logEvent.tagsValue;
    public final SqlColumn<ObjectNode> metric = logEvent.metric;

    public static final BasicColumn aggregateEventTime(final String timeAggregateType, String alias) {
        switch (timeAggregateType) {
            case TimeAggergateType
                    .ONE_HOUR:
                return ToStartOfHour.from(timestamp).as(alias);
            case TimeAggergateType
                    .ONE_DAY:
                return ToStartOfDay.from(timestamp).as(alias);
            case TimeAggergateType
                    .ONE_MINUTE:
                return ToStartOfMinute.from(timestamp).as(alias);
        }
        throw new IllegalArgumentException("不支持的日期聚合" + timeAggregateType);
    }


    public static final SqlColumn<String> columnOfTag(String tagName) {
        return new SqlColumn
                .Builder<String>()
                .withTable(logEvent)
                .withName(valueOfTagElement(tagName))
                .withJdbcType(JDBCType.VARCHAR)
                .withJavaType(String.class)
                .withAlias(tagName.replace("tag_", "")).build();
    }

    private static String valueOfTagElement(String tagName) {
        return "arrayElement(tags_value," +
                " indexOf(tags_key, '" + tagName + "'))";
    }


    public static final class LogEvent extends SqlTablePlus {

        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<Integer> eventId = column(LogEventColumns.COLUMN_EVENT_ID, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> appKey = column(LogEventColumns.COLUMN_APP_KEY, JDBCType.DATE);


        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> eventType = column(LogEventColumns.COLUMN_EVENT_TYPE, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<Date> time = column(LogEventColumns.COLUMN_EVENT_TIME, JDBCType.DATE);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<Date> timestamp = column(LogEventColumns.COLUMN_EVENT_TIMESTAMP, JDBCType.DATE);
//        @ColumnGroup({"basic", "detail"})
//        public final SqlColumn<String> appName = column(LogEventColumns.COLUMN_APP_NAME, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> version = column(LogEventColumns.COLUMN_APP_VERSION, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceModel = column(LogEventColumns.COLUMN_DEVICE_MODEL, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceName = column(LogEventColumns.COLUMN_DEVICE_NAME, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceOs = column(LogEventColumns.COLUMN_DEVICE_OS, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceOsVersion = column(LogEventColumns.COLUMN_DEVICE_OS_VERSION, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceUUID = column(LogEventColumns.COLUMN_DEVICE_UUID, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> deviceBrand = column(LogEventColumns.COLUMN_DEVICE_BRAND, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> userId = column(LogEventColumns.COLUMN_USER_ID, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> groupHash = column(LogEventColumns.COLUMN_GROUP_HASH, JDBCType.CHAR);
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<String> sessionId = column(LogEventColumns.COLUMN_SESSION_ID, JDBCType.CHAR);
        @ColumnGroup({"detail"})
        public final SqlColumn<ObjectNode> extra = column(LogEventColumns.COLUMN_EXTRA,
                JDBCType.CHAR,
                JacksonTypeHandler.class.getCanonicalName());
        @ColumnGroup({"basic", "detail"})
        public final SqlColumn<Long> groupId = column(LogEventColumns.COLUMN_GROUP_ID,
                JDBCType.NUMERIC);

        @ColumnGroup("detail")
        public final SqlColumn<List<String>> tagsKey = column(LogEventColumns.COLUMN_TAGS_KEY, JDBCType.CHAR);
        @ColumnGroup("detail")
        public final SqlColumn<List<String>> tagsValue = column(LogEventColumns.COLUMN_TAGS_VALUE, JDBCType.CHAR);
        @ColumnGroup("detail")
        public final SqlColumn<ObjectNode> metric = column(LogEventColumns.COLUMN_METRICS, JDBCType.CHAR);


        protected LogEvent() {
            super(LogEventColumns.TABLE_NAME);
        }


        public BindableColumn metricValueColumn(String name) {
            String metricName = name.replace("metrics.", "");
            return JsonExtractFloat.of(metric, metricName);
        }

        public BindableColumn tagColumn(String name) {
            String metricName = name.replace("tag_.", "");
            return JsonExtractFloat.of(metric, metricName);
        }


        @Override
        public <T> SqlColumn<T> columnOf(String name) {
            return super.columnOf(name);
        }
    }
}
