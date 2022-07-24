package com.knightboost.moonlight.dynamicsql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Created by Knight-ZXW on 2022/4/13
 * email: nimdanoob@163.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnGroup {
    String[] value();
}
