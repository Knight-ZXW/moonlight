package com.knightboost.moonlight.upms.api;

import com.alibaba.fastjson.JSONArray;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
public interface UpmsUserPermissionService {
    /**
     * 用户权限
     * @param datas 权限数据
     * @param id 用户id
     * @return
     */
    int permission(JSONArray datas, int id);
}
