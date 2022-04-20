package com.knightboost.moonlight.upms.constant;

import top.tangyh.basic.base.BaseResult;

/*
 * Created by Knight-ZXW on 2022/4/18
 * email: nimdanoob@163.com
 */
public class UpmsResult extends BaseResult {
    public UpmsResult(UpmsResultConstant upmsResultConstant,
                      Object data) {
        super(upmsResultConstant.getCode(),
                upmsResultConstant.getMessage(),
                data);
    }
}
