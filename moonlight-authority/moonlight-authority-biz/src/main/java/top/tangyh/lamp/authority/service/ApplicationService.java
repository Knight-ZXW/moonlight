package top.tangyh.lamp.authority.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperServiceImpl;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.security.shiro.ShiroUtil;
import top.tangyh.lamp.authority.dao.MLApplicationMapper;
import top.tangyh.lamp.authority.dao.MLApplicationUserRelMapper;
import top.tangyh.lamp.authority.dto.application.CreateApplicationDTO;
import top.tangyh.lamp.authority.dto.application.ListApplicationsDTO;
import top.tangyh.lamp.authority.entity.MLApplication;
import top.tangyh.lamp.authority.entity.MLApplicationUserRel;
import top.tangyh.lamp.authority.service.common.ApplicationServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */
@Service
public class ApplicationService extends SuperServiceImpl<MLApplicationMapper, MLApplication> implements ApplicationServiceInterface {

    @Autowired
    private MLApplicationUserRelMapper applicationUserRelMapper;


    public R<MLApplication> createApplication(CreateApplicationDTO createApplicationDTO) {
        long userId = ShiroUtil.getLoginUserId();
        boolean exist = getSuperMapper()
                .selectOne(Wraps.<MLApplication>lbQ()
                        .eq(MLApplication::getAppName, createApplicationDTO.getAppName())) != null;
        if (exist) {
            return R.fail("应用名 " + createApplicationDTO.getAppName() + " 已存在");
        }

        MLApplication mlApplication = new MLApplication();
        BeanUtil.copyProperties(createApplicationDTO, mlApplication);
        mlApplication.setCreateBy(userId);
        mlApplication.setAppKey(IdUtil.fastSimpleUUID());
        mlApplication.setAppSecret(IdUtil.fastSimpleUUID());
        save(mlApplication);
        addUserToApplication(userId, mlApplication);

        //todo  add user as  app Admin Role ?
        return R.success(mlApplication);
    }

    @Override
    public R<List<MLApplication>> myApplications(){
        ListApplicationsDTO listApplicationsDTO = new ListApplicationsDTO();
        listApplicationsDTO.setUserId(ShiroUtil.getLoginUserId());
        return listApplications(listApplicationsDTO);
    }

    @Override
    public R<List<MLApplication>> listApplications(ListApplicationsDTO listApplicationsDTO){
        List<Long> appIds = applicationUserRelMapper.selectList(
                Wraps.<MLApplicationUserRel>lbQ()
                        .eq(MLApplicationUserRel::getUserId, listApplicationsDTO.getUserId())
        ).stream().map(MLApplicationUserRel::getAppId).collect(Collectors.toList());

        List<MLApplication> mlApplications = getSuperMapper()
                .selectList(Wraps.<MLApplication>lbQ()
                        .in(MLApplication::getId, appIds)
                );
        return R.success(mlApplications);
    }


    /**
     * 将用户添加到应用中
     *
     * @return
     */
    public R<Boolean> addUserToApplication(Long userId, MLApplication mlApplication) {
        Long id = mlApplication.getId();
        boolean exists = applicationUserRelMapper.exists(Wraps.<MLApplicationUserRel>lbQ()
                .eq(MLApplicationUserRel::getAppId, mlApplication.getId())
                .eq(MLApplicationUserRel::getUserId, mlApplication.getId()));
        if (!exists) {
            MLApplicationUserRel rel = new MLApplicationUserRel();
            rel.setAppId(mlApplication.getId());
            rel.setUserId(userId);
            applicationUserRelMapper.insert(rel);
        }
        return R.success(true);
    }
}
