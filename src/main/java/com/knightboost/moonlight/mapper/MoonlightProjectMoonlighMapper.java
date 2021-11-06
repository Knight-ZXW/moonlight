package com.knightboost.moonlight.mapper;

import com.knightboost.moonlight.domain.MoonlightProject;

public interface MoonlightProjectMoonlighMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoonlightProject record);

    int insertSelective(MoonlightProject record);

    MoonlightProject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoonlightProject record);

    int updateByPrimaryKey(MoonlightProject record);
}