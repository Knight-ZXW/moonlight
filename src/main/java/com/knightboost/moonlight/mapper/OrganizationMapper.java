package com.knightboost.moonlight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knightboost.moonlight.domain.Organization;
import org.springframework.stereotype.Component;

/**
 * @Entity com.knightboost.moonlight.domain.Organization
 */
@Component
public interface OrganizationMapper extends BaseMapper<Organization> {

    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

}
