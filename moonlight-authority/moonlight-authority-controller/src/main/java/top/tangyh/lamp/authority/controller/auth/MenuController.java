package top.tangyh.lamp.authority.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.annotation.log.SysLog;
import top.tangyh.basic.annotation.security.PreAuth;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.controller.SuperCacheController;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.utils.BeanPlusUtil;
import top.tangyh.basic.utils.TreeUtil;
import top.tangyh.lamp.authority.dto.auth.MenuResourceTreeVO;
import top.tangyh.lamp.authority.dto.auth.MenuSaveDTO;
import top.tangyh.lamp.authority.dto.auth.MenuUpdateDTO;
import top.tangyh.lamp.authority.entity.auth.Menu;
import top.tangyh.lamp.authority.service.auth.MenuService;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * 菜单
 * </p>
 *
 * @author zuihou
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@Api(value = "Menu", tags = "菜单")
@PreAuth(replace = "authority:menu:")
public class MenuController extends SuperCacheController<MenuService, Long, Menu, Menu, MenuSaveDTO, MenuUpdateDTO> {

    @Override
    public R<Menu> handlerSave(MenuSaveDTO menuSaveDTO) {
        Menu menu = BeanPlusUtil.toBean(menuSaveDTO, Menu.class);
        baseService.saveWithCache(menu);
        return success(menu);
    }

    @Override
    public R<Menu> handlerUpdate(MenuUpdateDTO model) {
        Menu menu = BeanPlusUtil.toBean(model, Menu.class);
        baseService.updateWithCache(menu);
        return success(menu);
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        baseService.removeByIdWithCache(ids);
        return success();
    }

    /**
     * 查询系统中所有的的菜单树结构， 不用缓存，因为该接口很少会使用，就算使用，也会管理员维护菜单时使用
     *
     */
    @ApiOperation(value = "查询系统所有的菜单", notes = "查询系统所有的菜单")
    @PostMapping("/tree")
    @SysLog("查询系统所有的菜单")
    public R<List<Menu>> allTree() {
        List<Menu> list = baseService.list(Wraps.<Menu>lbQ().orderByAsc(Menu::getSortValue));
        return success(TreeUtil.buildTree(list));
    }

    /**
     * 查询系统所有的菜单和资源树， 不用缓存，因为该接口很少会使用，就算使用，也会管理员维护菜单时使用
     */
    @ApiOperation(value = "查询系统所有的菜单和资源树", notes = "查询系统所有的菜单和资源树")
    @PostMapping("/menuResourceTree")
    @SysLog("查询系统所有的菜单和资源树")
    public R<List<MenuResourceTreeVO>> menuResourceTree() {
        return success(baseService.findMenuResourceTree());
    }
    /**
     * 查询系统所有的数据权限  不用缓存，因为该接口很少会使用，就算使用，也会管理员维护菜单时使用
     */
    @ApiOperation(value = "查询系统所有的数据权限", notes = "查询系统所有的数据权限")
    @PostMapping("/findMenuDataScopeTree")
    @SysLog("查询系统所有的数据权限")
    public R<List<MenuResourceTreeVO>> findMenuDataScopeTree() {
        return success(baseService.findMenuDataScopeTree());
    }
}
