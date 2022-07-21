package com.knightboost.moonlight.apm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knightboost.moonlight.apm.chdao.LogEventMapper;
import com.knightboost.moonlight.apm.entity.LogEvent;
import org.springframework.stereotype.Service;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Service
public class LogEventService extends ServiceImpl<LogEventMapper, LogEvent> {


    public void insertLog(LogEvent logEvent) {
        getBaseMapper().insert(logEvent);
    }
}
