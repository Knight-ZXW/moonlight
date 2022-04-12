package top.tangyh.lamp.sms.service.impl;


import top.tangyh.basic.base.service.SuperServiceImpl;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.lamp.sms.dao.SmsSendStatusMapper;
import top.tangyh.lamp.sms.entity.SmsSendStatus;
import top.tangyh.lamp.sms.service.SmsSendStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 短信发送状态
 * </p>
 *
 * @author zuihou
 * @date 2019-08-01
 */
@Slf4j
@Service

public class SmsSendStatusServiceImpl extends SuperServiceImpl<SmsSendStatusMapper, SmsSendStatus> implements SmsSendStatusService {

    @Override
    @Transactional(readOnly = true)
    public List<SmsSendStatus> listByTaskId(Long id) {
        return list(Wraps.<SmsSendStatus>lbQ().eq(SmsSendStatus::getTaskId, id));
    }
}
