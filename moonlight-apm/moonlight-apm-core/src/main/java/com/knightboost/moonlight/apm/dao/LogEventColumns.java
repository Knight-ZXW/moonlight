package com.knightboost.moonlight.apm.dao;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public interface LogEventColumns {

    public static final String TABLE_NAME = "log_event";


    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_APP_KEY = "app_key";
    public static final String COLUMN_EVENT_TYPE = "event_type";

    public static final String COLUMN_EVENT_TIME = "event_time";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String COLUMN_APP_NAME = "app_name";
    public static final String COLUMN_APP_VERSION = "version";

    public static final String COLUMN_DEVICE_MODEL = "device_model";
    public static final String COLUMN_DEVICE_NAME = "device_name";
    public static final String COLUMN_DEVICE_OS = "device_os";
    public static final String COLUMN_DEVICE_OS_VERSION = "device_os_version";
    public static final String COLUMN_DEVICE_UUID = "device_uuid";
    public static final String COLUMN_DEVICE_BRAND = "device_brand";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_GROUP_HASH = "group_hash";
    public static final String COLUMN_SESSION_ID = "session_id";
    public static final String COLUMN_EXTRA = "extra";
    public static final String COLUMN_GROUP_ID = "group_id";
    public static final String COLUMN_GROUP_ID_ALIAS = "groupId";

    public static final String COLUMN_LEAK_CLASS = "leak_class";
    public static final String COLUMN_LEAK_PATHS = "leak_paths";
    public static final String COLUMN_TAGS_KEY = "tags_key";
    public static final String COLUMN_TAGS_VALUE = "tags_value";
    public static final String COLUMN_METRICS = "metrics";


    public static final String COLUMN_DATE = "datetime";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_DEVICE_COUNT = "device_count";

}
