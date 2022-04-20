package com.knightboost.moonlight.admin;

/*
 * Created by Knight-ZXW on 2022/4/17
 * email: nimdanoob@163.com
 */

import com.knightboost.moonlight.dto.AdminMenuDTO;
import com.knightboost.moonlight.mapstruct.AdminMenuMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangyh.lamp.authority.dto.auth.ResourceQueryDTO;
import top.tangyh.lamp.authority.entity.auth.Menu;
import top.tangyh.lamp.authority.entity.auth.Resource;
import top.tangyh.lamp.authority.service.auth.MenuService;
import top.tangyh.lamp.authority.service.auth.ResourceService;
import top.tangyh.lamp.authority.service.auth.RoleAuthorityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminMenuService {
    @Autowired
    MenuService menuService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleAuthorityService roleAuthorityService;


    public List<AdminMenuDTO> getMenuTree(Long userId) {
        List<Menu> visibleMenus = menuService.findVisibleMenu(null, userId);
        visibleMenus.sort(new Comparator<Menu>() {
            @Override
            public int compare(Menu menu1, Menu menu2) {
                int sort = menu1.getTreeGrade() - menu2.getTreeGrade();
                if (sort == 0) {
                    return menu1.getSortValue() - menu2.getSortValue();
                }
                return sort;
            }
        });
        ArrayList<AdminMenuDTO> menuTree = new ArrayList<>();
        HashMap<Long, AdminMenuDTO> menuMap = new HashMap<>();
        for (Menu visibleMenu : visibleMenus) {
            AdminMenuDTO menuDto = AdminMenuMap.mapper.menuToDto(visibleMenu);
            Long id = menuDto.getId();
            menuMap.put(id, menuDto);
            Long parentId = menuDto.getParentId();

            //获取能够访问此菜单的角色


            ResourceQueryDTO resourceQueryDto = new ResourceQueryDTO();
            resourceQueryDto.setMenuId(id);
            List<Resource> visibleResource = resourceService.findVisibleResource(resourceQueryDto);

            if (parentId ==0){
                menuTree.add(menuDto);
            }else {
                AdminMenuDTO parentMenu = menuMap.get(parentId);
                parentMenu.addChildren(menuDto);
            }
        }
        return menuTree;
    }

}
