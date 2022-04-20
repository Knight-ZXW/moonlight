package com.knightboost.moonlight.upms.service;

import com.knightboost.moonlight.upms.api.UpmsApiService;
import com.knightboost.moonlight.upms.entity.*;
import com.knightboost.moonlight.upms.mapper.UpmsPermissionMapper;
import com.knightboost.moonlight.upms.mapper.UpmsUserMapper;
import com.knightboost.moonlight.upms.mapper.UpmsUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
@Service
public class UpmsApiServiceImpl implements UpmsApiService {
    @Autowired
    UpmsUserMapper upmsUserMapper;

    @Autowired
    UpmsPermissionMapper upmsPermissionMapper;

    @Autowired
    UpmsRolePermission upmsRolePermission;

    @Autowired
    UpmsUserRoleMapper upmsUserRoleMapper;

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {

        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserIdByCache(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        return null;
    }

    @Override
    public List<UpmsUserPermission> selectUpmsUserPermissionByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        return null;
    }
}
