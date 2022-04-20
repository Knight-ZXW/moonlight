package com.knightboost.moonlight.upms.api;

import com.knightboost.moonlight.upms.entity.UpmsSystem;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
public interface UpmsSystemService {

    /**
     * 根据name获取UpmsSystem
     * @param name
     * @return
     */
    UpmsSystem selectUpmsSystemByName(String name);
}
