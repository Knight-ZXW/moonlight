package com.knightboost.moonlight.mapper;

import com.knightboost.moonlight.domain.GroupedMessage;

/**
 * @Entity com.knightboost.moonlight.domain.GroupedMessage
 */
public interface GroupedMessageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupedMessage record);

    int insertSelective(GroupedMessage record);

    GroupedMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupedMessage record);

    int updateByPrimaryKey(GroupedMessage record);

}
