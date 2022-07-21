package com.knightboost.moonlight.apm.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventHandleResult {
    private boolean success;
    private String errorMessage;
    private static final EventHandleResult successResult = new EventHandleResult(true,
            null);

    public static EventHandleResult success() {
            return successResult;
    }
}
