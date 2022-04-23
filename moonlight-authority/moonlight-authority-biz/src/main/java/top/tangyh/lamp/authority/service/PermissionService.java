package top.tangyh.lamp.authority.service;

import org.springframework.stereotype.Service;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.authority.dao.MLPermissionMapper;
import top.tangyh.lamp.authority.entity.MLPermission;
import top.tangyh.lamp.common.cache.CacheKeyDefinition;

/*
 * Created by Knight-ZXW on 2022/4/21
 * email: nimdanoob@163.com
 */
@Service
public class PermissionService extends SuperCacheServiceImpl<MLPermissionMapper,MLPermission> {


    public R<MLPermission> createPermission(){


        return R.fail("无权限");
    }

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new CacheKeyBuilder() {
            @Override
            public String getPrefix() {
                return CacheKeyDefinition.PERMISSION;
            }
        };
    }
}
