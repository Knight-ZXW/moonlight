package com.knightboost.moonlight.dto;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.tangyh.basic.base.entity.TreeEntity;
import top.tangyh.lamp.authority.dto.auth.RouterMeta;

import java.util.Date;
@Data
public class AdminMenuDTO extends TreeEntity<AdminMenuDTO,Long> {
    private static final long serialVersionUID = -3327478146308500708L;


    @ApiModelProperty(value = "菜单名称")
    private String menuName;


    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;


}
