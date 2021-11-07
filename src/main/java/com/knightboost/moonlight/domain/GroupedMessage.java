package com.knightboost.moonlight.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName apm_grouped_message
 */
@TableName(value ="apm_grouped_message")
@Data
public class GroupedMessage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Integer level;

    /**
     * 
     */
    private String message;

    /**
     * 
     */
    private String view;

    /**
     * 
     */
    private Integer numComments;

    /**
     * 
     */
    private String platform;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Integer timesSeen;

    /**
     * 
     */
    private Date firstSeen;

    /**
     * 
     */
    private Date lastSeen;

    /**
     * 
     */
    private Date resolvedAt;

    /**
     * 
     */
    private String data;

    /**
     * 
     */
    private Long projectId;

}