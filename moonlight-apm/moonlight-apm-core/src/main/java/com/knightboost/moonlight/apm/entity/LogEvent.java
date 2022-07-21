package com.knightboost.moonlight.apm.entity;

/*
 * Created by Knight-ZXW on 2022/7/20
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class LogEvent {
    private String eventId;
    private String eventType;
    private String appKey;
    private Date eventTimestamp;
    private String groupId;//issueId
    private String version;
    private String os;

    @TableField(value = "exception_frames.function",jdbcType = JdbcType.ARRAY)
    private List<String> exceptionFramesFunction =new ArrayList<>();

    @TableField(value = "exception_frames.in_app",jdbcType = JdbcType.ARRAY)
    private List<String> inApp =new ArrayList<>();

}
