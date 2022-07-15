package top.tangyh.lamp.authority.controller;

/*
 * Created by Knight-ZXW on 2022/4/25
 * email: nimdanoob@163.com
 */

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;
import top.tangyh.basic.security.shiro.ShiroUtil;
import top.tangyh.lamp.authority.dto.auth.VueRouter;
import top.tangyh.lamp.authority.entity.MLMenu;
import top.tangyh.lamp.authority.service.MenuService;

import java.util.List;

@RestController()
@RequestMapping("/menu")
@Api(value = "ApplicationController", tags = "New Application")
public class MenuController {

    @Autowired
    public MenuService menuService;

    @GetMapping("/myVueMenus")
    public R<List<VueRouter>> myVueMenus(){
        ShiroUtil.getLoginUserId();
        List<MLMenu> menus = menuService.getVisibleMenus(ShiroUtil.getLoginUserId());

        return R.success(null);
    }
}
