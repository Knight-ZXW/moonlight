package com.knightboost.moonlight.upms.entity;

import lombok.Data;
import top.tangyh.basic.base.entity.BaseEntity;

import java.io.Serializable;

@Data
public class UpmsSystem  extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer systemId;

    /**
     * 状态(-1:黑名单,1:正常)
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * 系统名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 系统标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 系统描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Long orders;
}