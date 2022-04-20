package com.knightboost.moonlight.upms.api;

import com.alibaba.fastjson.JSONArray;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
public interface UpmsPermissionService {
    JSONArray getTreeByRoleId(Integer roleId);

    JSONArray getTreeByUserId(Integer userId, Byte type);
}
