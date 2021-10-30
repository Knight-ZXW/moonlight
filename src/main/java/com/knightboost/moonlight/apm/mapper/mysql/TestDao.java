package com.knightboost.moonlight.apm.mapper.mysql;

import com.knightboost.moonlight.apm.model.Test;
import org.springframework.stereotype.Component;

@Component
public interface TestDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);
}