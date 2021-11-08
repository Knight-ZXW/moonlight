package com.knightboost.moonlight.mapper;

import com.knightboost.moonlight.domain.GroupedMessage;
import org.springframework.stereotype.Component;

/**
 * @Entity com.knightboost.moonlight.domain.GroupedMessage
 */
@Component
public interface GroupedMessageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupedMessage record);

    int insertSelective(GroupedMessage record);

    GroupedMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupedMessage record);

    int updateByPrimaryKey(GroupedMessage record);

}
