package com.knightboost.moonlight.upms.entity;

import lombok.Data;
import top.tangyh.basic.base.entity.BaseEntity;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
@Data
public class UpmsUserPermission extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userPermissionId;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 权限编号
     *
     * @mbg.generated
     */
    private Integer permissionId;

    /**
     * 权限类型(-1:减权限,1:增权限)
     *
     * @mbg.generated
     */
    private Byte type;
}
