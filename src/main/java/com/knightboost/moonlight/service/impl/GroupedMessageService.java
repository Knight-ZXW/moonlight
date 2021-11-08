package com.knightboost.moonlight.service.impl;


import com.knightboost.moonlight.domain.GroupedMessage;
import com.knightboost.moonlight.mapper.GroupedMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聚合日志 管理服务
 */
@Service
public class GroupedMessageService {
    @Autowired
    GroupedMessageMapper mapper;

    /**
     * 获取 聚合数据 列表
     * @return
     */
    public List<GroupedMessage> listGroupedMessage(){
        return null;
    }



}
