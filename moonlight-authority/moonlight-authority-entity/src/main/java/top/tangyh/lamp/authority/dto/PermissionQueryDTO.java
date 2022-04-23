package top.tangyh.lamp.authority.dto;

import io.swagger.annotations.ApiModelProperty;

/*
 * Created by Knight-ZXW on 2022/4/22
 * email: nimdanoob@163.com
 */
public class PermissionQueryDTO {
    /**
     * 登录人用户id
     */
    @ApiModelProperty(value = "指定用户id", notes = "指定用户id，前端不传则自动获取")
    private Long userId;
}
