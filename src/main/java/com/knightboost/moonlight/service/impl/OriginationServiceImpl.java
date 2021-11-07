package com.knightboost.moonlight.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knightboost.moonlight.domain.ApmLog;
import com.knightboost.moonlight.domain.Organization;
import com.knightboost.moonlight.mapper.OrganizationMapper;
import com.knightboost.moonlight.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class OriginationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService {

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    public void createOrganization(String name){
        Organization organization = new Organization();
        organization.setName(name);
        organizationMapper.insertSelective(organization);
    }

}




