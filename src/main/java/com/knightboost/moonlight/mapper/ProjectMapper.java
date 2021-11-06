package com.knightboost.moonlight.mapper;

import com.knightboost.moonlight.domain.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.knightboost.moonlight.domain.Project
 */
public interface ProjectMapper extends BaseMapper<Project> {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}




