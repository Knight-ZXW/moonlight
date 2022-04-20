package com.knightboost.moonlight.upms.entity;

import lombok.Data;
import top.tangyh.basic.base.entity.BaseEntity;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
@Data
public class UpmsUserRole extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userRoleId;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 角色编号
     *
     * @mbg.generated
     */
    private Integer roleId;
}
