package com.knightboost.moonlight.apm.service;

import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knightboost.moonlight.apm.entity.ApmIssue;
import com.knightboost.moonlight.apm.dao.ApmIssueMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * Created by Knight-ZXW on 2022/7/17
 * email: nimdanoob@163.com
 */
@Service
@AllArgsConstructor
public class ApmIssueService extends ServiceImpl<ApmIssueMapper, ApmIssue> {

    private final UidGenerator uidGenerator;

    public Long insertIssue(ApmIssue apmIssue){
        long id = uidGenerator.getUid();
        apmIssue.setId(id);
        getBaseMapper().insert(apmIssue);
        return id;
    }

}
