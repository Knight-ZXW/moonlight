package com.knightboost.moonlight.upms.entity;

import top.tangyh.basic.base.entity.BaseEntity;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
public class UpmsRolePermission extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer rolePermissionId;

    /**
     * 角色编号
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 权限编号
     *
     * @mbg.generated
     */
    private Integer permissionId;
}
