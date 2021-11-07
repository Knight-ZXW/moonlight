package com.knightboost.moonlight.mapper.ck;

import com.knightboost.moonlight.domain.ApmLog;

/**
 * @Entity com.knightboost.moonlight.domain.ApmLog
 */
public interface ApmLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApmLog record);

    int insertSelective(ApmLog record);

    ApmLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApmLog record);

    int updateByPrimaryKey(ApmLog record);

}
