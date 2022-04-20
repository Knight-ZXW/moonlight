package com.knightboost.moonlight.upms.entity;

import top.tangyh.basic.base.entity.BaseEntity;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
public class UpmsRole extends BaseEntity<Long> {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 角色标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 角色描述
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
