package com.knightboost.moonlight.service;

import com.knightboost.moonlight.domain.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
/**
 *
 */
public interface OrganizationService extends IService<Organization> {

    void createOrganization(String name);
}
