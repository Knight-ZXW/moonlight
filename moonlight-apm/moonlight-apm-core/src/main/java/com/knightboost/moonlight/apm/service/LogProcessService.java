package com.knightboost.moonlight.apm.service;

import com.knightboost.moonlight.apm.entity.LogEvent;
import org.springframework.stereotype.Service;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Service
public class LogProcessService {

    public void onLogEvent(LogEvent logEvent){
        String eventType = logEvent.getEventType();
    }
}
