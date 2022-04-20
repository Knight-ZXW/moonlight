package com.knightboost.moonlight.upms.entity;

import lombok.Data;
import top.tangyh.basic.base.entity.BaseEntity;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
@Data
public class UpmsUserOrganization extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userOrganizationId;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 组织编号
     *
     * @mbg.generated
     */
    private Integer organizationId;
}
