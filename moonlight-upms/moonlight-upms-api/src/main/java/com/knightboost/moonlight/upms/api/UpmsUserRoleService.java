package com.knightboost.moonlight.upms.api;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
public interface UpmsUserRoleService {
    /**
     * 用户角色
     * @param roleIds 角色ids
     * @param id 用户id
     * @return
     */
    int role(String[] roleIds, int id);
}
