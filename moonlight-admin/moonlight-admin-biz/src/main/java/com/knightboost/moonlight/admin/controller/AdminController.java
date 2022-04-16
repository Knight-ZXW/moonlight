package com.knightboost.moonlight.admin.controller;

import com.knightboost.moonlight.admin.AdminMenuService;
import com.knightboost.moonlight.dto.AdminMenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;
import top.tangyh.basic.context.ContextUtil;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */
@RestController()
@RequestMapping("/admin")
@Api(value = "Admin", tags = "后台管理")
public class AdminController {

    @Autowired
    public AdminMenuService adminMenuService;

    @ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
    @GetMapping("/menuList")
    public R<List<AdminMenuDTO>> getMenuList(){
        List<AdminMenuDTO> r = adminMenuService.getMenuTree(ContextUtil.getUserId());
        return R.success(r);
    }
}
