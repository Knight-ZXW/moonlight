package com.knightboost.moonlight.apm.dao;

import com.knightboost.moonlight.dynamicsql.SqlTablePlus;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.util.Date;

/*
 * Created by Knight-ZXW on 2022/7/22
 * email: nimdanoob@163.com
 */
public class ApmIssueDSS {

    public static final String TABLE_NAME = "apm_issue";
    public static final ApmIssue apmIssue = new ApmIssue();
    public final static SqlColumn<Integer> id = apmIssue.id;
    public final static SqlColumn<String> appKey = apmIssue.appKey;
    public final static SqlColumn<String> platform = apmIssue.platform;
    public final static SqlColumn<String> issueType = apmIssue.issueType;
    public final static SqlColumn<String> title = apmIssue.title;
    public final static SqlColumn<String> message = apmIssue.message;
    public final static SqlColumn<String> data = apmIssue.data;
    public final static SqlColumn<String> hash = apmIssue.hash;
    public final static SqlColumn<Integer> state = apmIssue.state;
    public final static SqlColumn<Integer> level = apmIssue.level;
    public final static SqlColumn<Long> assigneeUserId = apmIssue.assigneeUserId;
    public final static SqlColumn<Date> resolvedAt = apmIssue.resolvedAt;
    public final static SqlColumn<String> firstSeenVersion = apmIssue.firstSeenVersion;
    public final static SqlColumn<String> lastSeenVersion = apmIssue.lastSeenVersion;
    public final static SqlColumn<Date> firstSeen = apmIssue.firstSeen;
    public final static SqlColumn<Date> lastSeen = apmIssue.lastSeen;
    public final static SqlColumn<Date> createTime = apmIssue.createTime;
    public final static SqlColumn<Date> modifyTime = apmIssue.modifyTime;
    public final static SqlColumn<Integer> isDel = apmIssue.isDel;
    public final static SqlColumn<String> subIssueType = apmIssue.subIssueType;


    public static final class ApmIssue extends SqlTablePlus {

        public final SqlColumn<Integer> id = column("id", JDBCType.BIGINT);
        public final SqlColumn<String> appKey = column("app_key", JDBCType.VARCHAR);
        public final SqlColumn<String> platform = column("platform", JDBCType.VARCHAR);
        public final SqlColumn<String> issueType = column("issue_type", JDBCType.VARCHAR);
        public final SqlColumn<String> subIssueType = column("sub_issue_type", JDBCType.VARCHAR);
        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);
        public final SqlColumn<String> message = column("message", JDBCType.VARCHAR);
        public final SqlColumn<String> data = column("data", JDBCType.VARCHAR);
        public final SqlColumn<String> hash = column("hash", JDBCType.VARCHAR);
        public final SqlColumn<Integer> state = column("state", JDBCType.INTEGER);
        public final SqlColumn<Integer> level = column("level", JDBCType.INTEGER);
        public final SqlColumn<Long> assigneeUserId = column("assignee_user_id", JDBCType.INTEGER);
        public final SqlColumn<Date> resolvedAt = column("resolved_at", JDBCType.TIMESTAMP);
        public final SqlColumn<String> firstSeenVersion = column("first_seen_version", JDBCType.VARCHAR);
        public final SqlColumn<String> lastSeenVersion = column("last_seen_version", JDBCType.VARCHAR);
        public final SqlColumn<Date> firstSeen = column("first_seen", JDBCType.TIMESTAMP);
        public final SqlColumn<Date> lastSeen = column("last_seen", JDBCType.TIMESTAMP);
        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);
        public final SqlColumn<Date> modifyTime = column("modify_time", JDBCType.TIMESTAMP);
        public final SqlColumn<Integer> isDel = column("is_del", JDBCType.INTEGER);

        protected ApmIssue() {
            super(TABLE_NAME);
        }

    }
}
