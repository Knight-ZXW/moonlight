package com.knightboost.moonlight.upms.entity;

import lombok.Data;
import top.tangyh.basic.base.entity.BaseEntity;
/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
@Data
public class UpmsOrganization extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer organizationId;

    /**
     * 所属上级
     *
     * @mbg.generated
     */
    private Integer pid;

    /**
     * 组织名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 组织描述
     *
     * @mbg.generated
     */
    private String description;
}
