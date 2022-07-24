package com.knightboost.moonlight.apm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knightboost.moonlight.apm.entity.ApmIssue;
import com.knightboost.moonlight.apm.entity.LogEvent;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Created by Knight-ZXW on 2022/7/17
 * email: nimdanoob@163.com
 */
@Repository
public interface ApmIssueMapper extends BaseMapper<ApmIssue> , CommonCountMapper, CommonDeleteMapper,
        CommonInsertMapper<LogEvent>, CommonSelectMapper,
        CommonUpdateMapper {


    //TODO
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    List<ApmIssue> selectIssues(SelectStatementProvider selectStatement);
    
}
