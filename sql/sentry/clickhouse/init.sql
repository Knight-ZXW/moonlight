
create table discover_local
(
    event_id   UUID,
    project_id UInt64,
    type LowCardinality(String),
    timestamp  DateTime,
    platform LowCardinality(String),
    environment LowCardinality(Nullable(String)),
    release LowCardinality(Nullable(String)),
    dist LowCardinality(Nullable(String)),
    transaction_name LowCardinality(Nullable(String)),
    message Nullable(String),
    title Nullable(String),
    user LowCardinality(String),
    user_hash  UInt64,
    user_id Nullable(String),
    user_name Nullable(String),
    user_email Nullable(String),
    ip_address_v4 Nullable(IPv4),
    ip_address_v6 Nullable(IPv6),
    sdk_name LowCardinality(Nullable(String)),
    sdk_version LowCardinality(Nullable(String)),
    http_method LowCardinality(Nullable(String)),
    http_referer Nullable(String),
    `tags.key` Array(String),
    `tags.value` Array(String),
    _tags_hash_map Array(UInt64),
    `contexts.key` Array(String),
    `contexts.value` Array(String),
    deleted    UInt8
)
    engine = Merge('default', '^errors_local$|^transactions_local$');

create table errors_local
(
    project_id                   UInt64,
    timestamp                    DateTime,
    event_id                     UUID,
    platform LowCardinality(String),
    environment LowCardinality(Nullable(String)),
    release LowCardinality(Nullable(String)),
    dist LowCardinality(Nullable(String)),
    ip_address_v4 Nullable(IPv4),
    ip_address_v6 Nullable(IPv6),
    user                         String     default '',
    user_hash                    UInt64 materialized cityHash64(user),
    user_id Nullable(String),
    user_name Nullable(String),
    user_email Nullable(String),
    sdk_name LowCardinality(Nullable(String)),
    sdk_version LowCardinality(Nullable(String)),
    http_method LowCardinality(Nullable(String)),
    http_referer Nullable(String),
    `tags.key` Array(String),
    `tags.value` Array(String),
    _tags_hash_map Array(UInt64) materialized arrayMap(
        (k, v) -> cityHash64(concat(replaceRegexpAll(k, '(\\=|\\\\)', '\\\\\\1'), '=', v)), tags.key, tags.value),
    `contexts.key` Array(String),
    `contexts.value` Array(String),
    transaction_name LowCardinality(String) default '',
    transaction_hash             UInt64 materialized cityHash64(transaction_name),
    span_id Nullable(UInt64),
    trace_id Nullable(UUID),
    partition                    UInt16,
    offset                       UInt64,
    message_timestamp            DateTime,
    retention_days               UInt16,
    deleted                      UInt8,
    group_id                     UInt64,
    primary_hash                 UUID,
    received                     DateTime,
    message                      String,
    title                        String,
    culprit                      String,
    level LowCardinality(String),
    location Nullable(String),
    version LowCardinality(Nullable(String)),
    type LowCardinality(String),
    `exception_stacks.type` Array(Nullable(String)),
    `exception_stacks.value` Array(Nullable(String)),
    `exception_stacks.mechanism_type` Array(Nullable(String)),
    `exception_stacks.mechanism_handled` Array(Nullable(UInt8)),
    `exception_frames.abs_path` Array(Nullable(String)),
    `exception_frames.colno` Array(Nullable(UInt32)),
    `exception_frames.filename` Array(Nullable(String)),
    `exception_frames.function` Array(Nullable(String)),
    `exception_frames.lineno` Array(Nullable(UInt32)),
    `exception_frames.in_app` Array(Nullable(UInt8)),
    `exception_frames.package` Array(Nullable(String)),
    `exception_frames.module` Array(Nullable(String)),
    `exception_frames.stack_level` Array(Nullable(UInt16)),
    sdk_integrations Array(String),
    `modules.name` Array(String),
    `modules.version` Array(String)
)
    engine = ReplacingMergeTree(deleted) PARTITION BY (retention_days, toMonday(timestamp)) ORDER BY (project_id,
                                                                                                      toStartOfDay(timestamp),
                                                                                                      primary_hash,
                                                                                                      cityHash64(event_id)) SAMPLE BY cityHash64(event_id) TTL timestamp + toIntervalDay(retention_days) SETTINGS index_granularity = 8192;

create table groupassignee_local
(
    offset         UInt64,
    record_deleted UInt8,
    project_id     UInt64,
    group_id       UInt64,
    date_added Nullable(DateTime),
    user_id Nullable(UInt64),
    team_id Nullable(UInt64)
)
    engine = ReplacingMergeTree(offset) ORDER BY (project_id, group_id) SETTINGS index_granularity = 8192;

create table groupedmessage_local
(
    offset         UInt64,
    record_deleted UInt8,
    project_id     UInt64,
    id             UInt64,
    status Nullable(UInt8),
    last_seen Nullable(DateTime),
    first_seen Nullable(DateTime),
    active_at Nullable(DateTime),
    first_release_id Nullable(UInt64)
)
    engine = ReplacingMergeTree(offset) ORDER BY (project_id, id) SAMPLE BY id SETTINGS index_granularity = 8192;

create table migrations_local
(
    group        String,
    migration_id String,
    timestamp    DateTime,
    status Enum8('completed' = 0, 'in_progress' = 1, 'not_started' = 2),
    version      UInt64 default 1
)
    engine = ReplacingMergeTree(version) ORDER BY (group, migration_id) SETTINGS index_granularity = 8192;

create table outcomes_hourly_local
(
    org_id     UInt64,
    project_id UInt64,
    key_id     UInt64,
    timestamp  DateTime,
    outcome    UInt8,
    reason LowCardinality(String),
    times_seen UInt64
)
    engine = SummingMergeTree() PARTITION BY toMonday(timestamp) ORDER BY (org_id, project_id, key_id, outcome, reason, timestamp) SETTINGS index_granularity = 256;

create table outcomes_raw_local
(
    org_id     UInt64,
    project_id UInt64,
    key_id Nullable(UInt64),
    timestamp  DateTime,
    outcome    UInt8,
    reason LowCardinality(Nullable(String)),
    event_id Nullable(UUID)
)
    engine = MergeTree() PARTITION BY toMonday(timestamp) ORDER BY (org_id, project_id, timestamp) SETTINGS index_granularity = 16384;

create table sentry_local
(
    event_id                     FixedString(32),
    project_id                   UInt64,
    group_id                     UInt64,
    timestamp                    DateTime,
    deleted                      UInt8,
    retention_days               UInt16,
    platform Nullable(String),
    message Nullable(String),
    primary_hash Nullable(FixedString(32)),
    received Nullable(DateTime),
    search_message Nullable(String),
    title Nullable(String),
    location Nullable(String),
    user_id Nullable(String),
    username Nullable(String),
    email Nullable(String),
    ip_address Nullable(String),
    geo_country_code Nullable(String),
    geo_region Nullable(String),
    geo_city Nullable(String),
    sdk_name Nullable(String),
    sdk_version Nullable(String),
    type Nullable(String),
    version Nullable(String),
    offset Nullable(UInt64),
    partition Nullable(UInt16),
    message_timestamp            DateTime,
    os_build Nullable(String),
    os_kernel_version Nullable(String),
    device_name Nullable(String),
    device_brand Nullable(String),
    device_locale Nullable(String),
    device_uuid Nullable(String),
    device_model_id Nullable(String),
    device_arch Nullable(String),
    device_battery_level Nullable(Float32),
    device_orientation Nullable(String),
    device_simulator Nullable(UInt8),
    device_online Nullable(UInt8),
    device_charging Nullable(UInt8),
    level Nullable(String),
    logger Nullable(String),
    server_name Nullable(String),
    transaction Nullable(String),
    environment Nullable(String),
    `sentry:release` Nullable(String),
    `sentry:dist` Nullable(String),
    `sentry:user` Nullable(String),
    site Nullable(String),
    url Nullable(String),
    app_device Nullable(String),
    device Nullable(String),
    device_family Nullable(String),
    runtime Nullable(String),
    runtime_name Nullable(String),
    browser Nullable(String),
    browser_name Nullable(String),
    os Nullable(String),
    os_name Nullable(String),
    os_rooted Nullable(UInt8),
    `tags.key` Array(String),
    `tags.value` Array(String),
    _tags_flattened              String,
    _tags_hash_map Array(UInt64) materialized arrayMap(
        (k, v) -> cityHash64(concat(replaceRegexpAll(k, '(\\=|\\\\)', '\\\\\\1'), '=', v)), tags.key, tags.value),
    `contexts.key` Array(String),
    `contexts.value` Array(String),
    http_method Nullable(String),
    http_referer Nullable(String),
    `exception_stacks.type` Array(Nullable(String)),
    `exception_stacks.value` Array(Nullable(String)),
    `exception_stacks.mechanism_type` Array(Nullable(String)),
    `exception_stacks.mechanism_handled` Array(Nullable(UInt8)),
    `exception_frames.abs_path` Array(Nullable(String)),
    `exception_frames.filename` Array(Nullable(String)),
    `exception_frames.package` Array(Nullable(String)),
    `exception_frames.module` Array(Nullable(String)),
    `exception_frames.function` Array(Nullable(String)),
    `exception_frames.in_app` Array(Nullable(UInt8)),
    `exception_frames.colno` Array(Nullable(UInt32)),
    `exception_frames.lineno` Array(Nullable(UInt32)),
    `exception_frames.stack_level` Array(UInt16),
    culprit Nullable(String),
    sdk_integrations Array(String),
    `modules.name` Array(String),
    `modules.version` Array(String)
)
    engine = ReplacingMergeTree(deleted) PARTITION BY (toMonday(timestamp), if(retention_days = 30, 30, 90)) PRIMARY KEY (project_id,
                                                                                                                          toStartOfDay(timestamp),
                                                                                                                          cityHash64(toString(event_id))) ORDER BY (project_id,
                                                                                                                                                                    toStartOfDay(timestamp),
                                                                                                                                                                    cityHash64(toString(event_id))) SAMPLE BY cityHash64(toString(event_id)) SETTINGS index_granularity = 8192;

create table sessions_hourly_local
(
    org_id     UInt64,
    project_id UInt64,
    started    DateTime,
    release LowCardinality(String),
    environment LowCardinality(String),
    duration_quantiles AggregateFunction(quantilesIf(0.5, 0.9), UInt32, UInt8),
    sessions AggregateFunction(countIf, UUID, UInt8),
    users AggregateFunction(uniqIf, UUID, UInt8),
    sessions_crashed AggregateFunction(countIf, UUID, UInt8),
    sessions_abnormal AggregateFunction(countIf, UUID, UInt8),
    sessions_errored AggregateFunction(uniqIf, UUID, UInt8),
    users_crashed AggregateFunction(uniqIf, UUID, UInt8),
    users_abnormal AggregateFunction(uniqIf, UUID, UInt8),
    users_errored AggregateFunction(uniqIf, UUID, UInt8)
)
    engine = AggregatingMergeTree() PARTITION BY toMonday(started) ORDER BY (org_id, project_id, release, environment, started) SETTINGS index_granularity = 256;

create table sessions_raw_local
(
    session_id     UUID,
    distinct_id    UUID,
    seq            UInt64,
    org_id         UInt64,
    project_id     UInt64,
    retention_days UInt16,
    duration       UInt32,
    status         UInt8,
    errors         UInt16,
    received       DateTime,
    started        DateTime,
    release LowCardinality(String),
    environment LowCardinality(String)
)
    engine = MergeTree() PARTITION BY toMonday(started) ORDER BY (org_id, project_id, release, environment, started) SETTINGS index_granularity = 16384;

create table test_table
(
    id Int32,
    tagsKey Array(String),
    tagsValue Array(String)
)
    engine = Memory;

create table transactions_local
(
    project_id                     UInt64,
    event_id                       UUID,
    trace_id                       UUID,
    span_id                        UInt64,
    transaction_name LowCardinality(String),
    transaction_hash               UInt64 materialized cityHash64(transaction_name),
    transaction_op LowCardinality(String),
    transaction_status             UInt8  default 2,
    start_ts                       DateTime,
    start_ms                       UInt16,
    finish_ts                      DateTime,
    finish_ms                      UInt16,
    duration                       UInt32,
    platform LowCardinality(String),
    environment LowCardinality(Nullable(String)),
    release LowCardinality(Nullable(String)),
    dist LowCardinality(Nullable(String)),
    ip_address_v4 Nullable(IPv4),
    ip_address_v6 Nullable(IPv6),
    user                           String default '',
    user_hash                      UInt64 materialized cityHash64(user),
    user_id Nullable(String),
    user_name Nullable(String),
    user_email Nullable(String),
    sdk_name LowCardinality(String)       default '',
    sdk_version LowCardinality(String)    default '',
    http_method LowCardinality(Nullable(String)),
    http_referer Nullable(String),
    `tags.key` Array(String),
    `tags.value` Array(String),
    _tags_flattened                String,
    _tags_hash_map Array(UInt64)   materialized arrayMap(
        (k, v) -> cityHash64(concat(replaceRegexpAll(k, '(\\=|\\\\)', '\\\\\\1'), '=', v)), tags.key, tags.value),
    `contexts.key` Array(String),
    `contexts.value` Array(String),
    _contexts_flattened            String,
    `measurements.key` Array(LowCardinality(String)),
    `measurements.value` Array(Float64),
    partition                      UInt16,
    offset                         UInt64,
    message_timestamp              DateTime,
    retention_days                 UInt16,
    deleted                        UInt8,
    type LowCardinality(String)    materialized 'transaction',
    message LowCardinality(String) materialized transaction_name,
    title LowCardinality(String)   materialized transaction_name,
    timestamp                      DateTime materialized finish_ts
)
    engine = ReplacingMergeTree(deleted) PARTITION BY (retention_days, toMonday(finish_ts)) ORDER BY (project_id,
                                                                                                      toStartOfDay(finish_ts),
                                                                                                      transaction_name,
                                                                                                      cityHash64(span_id)) SAMPLE BY cityHash64(span_id) TTL finish_ts + toIntervalDay(retention_days) SETTINGS index_granularity = 8192;

