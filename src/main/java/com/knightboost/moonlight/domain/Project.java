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
 * @TableName moonlight_project
 */
@TableName(value ="moonlight_project")
@Data
public class Project implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String platform;

    /**
     * 
     */
    private long organizationId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private String publicKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}