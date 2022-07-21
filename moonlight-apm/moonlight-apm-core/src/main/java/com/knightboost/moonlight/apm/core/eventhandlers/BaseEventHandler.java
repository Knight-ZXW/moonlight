package com.knightboost.moonlight.apm.core.eventhandlers;

import com.knightboost.moonlight.apm.core.EventHandleResult;
import com.knightboost.moonlight.apm.core.EventHandler;
import com.knightboost.moonlight.apm.entity.LogEvent;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
public class BaseEventHandler implements EventHandler {
    @Override
    public EventHandleResult handleEvent(LogEvent logEvent) {
        return EventHandleResult.success();
    }
}
