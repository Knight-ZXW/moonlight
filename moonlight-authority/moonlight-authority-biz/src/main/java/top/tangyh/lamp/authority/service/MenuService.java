package top.tangyh.lamp.authority.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKey;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.basic.utils.ArgumentAssert;
import top.tangyh.basic.utils.TreeUtil;
import top.tangyh.basic.utils.ValidatorUtil;
import top.tangyh.lamp.authority.dao.MLMenuMapper;
import top.tangyh.lamp.authority.entity.MLMenu;
import top.tangyh.lamp.authority.enumeration.auth.ResourceTypeEnum;
import top.tangyh.lamp.common.cache.CacheKeyDefinition;
import top.tangyh.lamp.common.cache.auth.UserMenuCacheKeyBuilder;
import top.tangyh.lamp.common.constant.DefValConstants;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static top.tangyh.basic.utils.StrPool.DEF_PARENT_ID;

/*
 * Created by Knight-ZXW on 2022/4/24
 * email: nimdanoob@163.com
 */
@Service
@Slf4j
public class MenuService extends SuperCacheServiceImpl<MLMenuMapper, MLMenu> {

    //
    public boolean updateMenu(MLMenu menu, boolean updateCache) {
        return false;
    }

    public boolean createMenu(MLMenu menu) {
        ArgumentAssert.notEmpty(menu.getPath(), "请填写【地址栏路径】");
        ArgumentAssert.notEmpty(menu.getComponent(), "请填写【页面路径】");
        ArgumentAssert.isFalse(checkName(null, menu.getLabel()), "【名称】:{}重复", menu.getLabel());
        if (!ValidatorUtil.isUrl(menu.getPath())) {
            ArgumentAssert.isFalse(checkPath(null, menu.getPath()), "【地址栏路径】:{}重复", menu.getPath());
        }

        fill(menu);
        menu.setState(Convert.toBool(menu.getState(), true));
        menu.setIsGeneral(Convert.toBool(menu.getIsGeneral(), false));
        menu.setParentId(Convert.toLong(menu.getParentId(), DEF_PARENT_ID));


        checkGeneral(menu, false);
        save(menu);
        return true;
    }


    public boolean updateMenu(MLMenu menu) {
        MLMenu old = getById(menu);
        ArgumentAssert.notNull(old, "您修改的菜单已不存在");
        checkGeneral(menu, true);
        fill(menu);
        return this.updateById(menu);
    }

    public List<MLMenu> getVisibleMenus(Long userId) {
        CacheKey userMenuKey = new UserMenuCacheKeyBuilder().key(userId);
        final List<MLMenu> visibleMenu = new ArrayList<>();
        List<Long> list;
        List<MLMenu> mlMenus = baseMapper.selectList(Wrappers.<MLMenu>lambdaQuery()
                .eq(MLMenu::getIsGeneral, true));
        visibleMenu.addAll(mlMenus);
        return visibleMenu;
    }


    /**
     * 清除所有用户的菜单缓存
     */
    private void clearAllUserMenuCache() {

    }

    public List<MLMenu> findChildrenByParentId(Long parentId) {
        ArgumentAssert.notNull(parentId, "parentId 不能为空");
        return list(Wrappers.<MLMenu>lambdaQuery()
                .in(MLMenu::getParentId, parentId).orderByAsc(MLMenu::getSortValue));
    }


    public Boolean checkPath(Long id, String path) {
        return baseMapper.selectCount(Wrappers.<MLMenu>lambdaQuery().
                ne(MLMenu::getId, id).eq(MLMenu::getPath, path)) > 0;
    }

    private void checkGeneral(MLMenu data, boolean isUpdate) {
        if (data.getIsGeneral() == null) {
            return;
        }

        if (data.getIsGeneral()) {
            // isGeneral 子节点 改成是，父节点全是
            if (!TreeUtil.isRoot(data.getParentId())) {
                MLMenu parent = getById(data.getParentId());
                ArgumentAssert.notNull(parent, "父节点不存在");
                ArgumentAssert.isTrue(parent.getIsGeneral(), "请先将父节点{} “公共菜单”字段修改为：“是”，再修改本节点", parent.getLabel());
            }
            return;
        }

        if (isUpdate) {
            // isGeneral 父节点 改成否，子节点全否
            List<MLMenu> childrenList = findChildrenByParentId(data.getId());
            if (CollUtil.isNotEmpty(childrenList)) {
                childrenList.forEach(item -> {
                    ArgumentAssert.isFalse(item.getIsGeneral(), "请先将子节点{} “公共资源”字段修改为：“否”，在修改本节点", item.getLabel());
                });
            }
        }
    }


    public Boolean checkName(Long id, String name) {
        return baseMapper.selectCount(Wrappers.<MLMenu>lambdaQuery()
                .ne(MLMenu::getId, id)
                .in(MLMenu::getResourceType, ResourceTypeEnum.MENU.getCode())
                .eq(MLMenu::getLabel, name)) > 0;
    }


    private void fill(MLMenu menu) {
        if (menu.getParentId() == null || menu.getParentId() <= 0) {
            menu.setParentId(DefValConstants.PARENT_ID);
            menu.setTreePath(DefValConstants.ROOT_PATH);
            menu.setTreeGrade(DefValConstants.TREE_GRADE);
        } else {
            MLMenu parent = getByIdCache(menu.getParentId());
            ArgumentAssert.notNull(parent, "请正确填写父级");
            menu.setTreeGrade(parent.getTreeGrade() + 1);
            menu.setTreePath(TreeUtil.getTreePath(parent.getTreePath(), parent.getId()));
        }
    }

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new CacheKeyBuilder() {
            @Override
            public String getPrefix() {
                return CacheKeyDefinition.MENU;
            }

            @Override
            public Duration getExpire() {
                return Duration.ofHours(24);
            }
        };
    }
}
