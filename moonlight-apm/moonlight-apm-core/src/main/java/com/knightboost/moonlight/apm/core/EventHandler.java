package com.knightboost.moonlight.apm.core;

import com.knightboost.moonlight.apm.entity.LogEvent;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
public interface EventHandler {
    public EventHandleResult handleEvent(LogEvent logEvent);
}
