package com.knightboost.moonlight.apm.entity;

/*
 * Created by Knight-ZXW on 2022/7/20
 * email: nimdanoob@163.com
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("log_event")
public class LogEvent {
    private String eventId;
    private String appKey;
    //TODO rename to issueId ?
    private Long groupId;//issueId
    private Date timestamp;

    private String groupHash;//issueId
    private String platform;
    private String title;
    private String location;

    private String userId;
    private String eventType;
    private Date eventTimestamp;
    private String version;
    private String deviceOs;


}
