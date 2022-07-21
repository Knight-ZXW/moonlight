package com.knightboost.moonlight.apm.core.annotation;

/*
 * Created by Knight-ZXW on 2022/7/21
 * email: nimdanoob@163.com
 */
public @interface TargetEvents {
    /**
     * 所能处理的日志类型
     */
    String[] eventTypes();
}
