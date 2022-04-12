package top.tangyh.lamp.sms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.controller.QueryController;
import top.tangyh.basic.base.controller.SuperSimpleController;
import top.tangyh.basic.base.request.PageParams;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.database.mybatis.conditions.query.QueryWrap;
import top.tangyh.lamp.sms.dto.SmsSendStatusPageQuery;
import top.tangyh.lamp.sms.entity.SmsSendStatus;
import top.tangyh.lamp.sms.service.SmsSendStatusService;

/**
 * <p>
 * 前端控制器
 * 短信发送状态
 * </p>
 *
 * @author zuihou
 * @date 2019-08-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/smsSendStatus")
@Api(value = "SmsSendStatus", tags = "短信发送状态")
public class SmsSendStatusController extends SuperSimpleController<SmsSendStatusService, SmsSendStatus>
        implements QueryController<SmsSendStatus, Long, SmsSendStatusPageQuery> {

    @Override
    public IPage<SmsSendStatus> query(PageParams<SmsSendStatusPageQuery> params) {
        IPage<SmsSendStatus> page = params.buildPage(SmsSendStatus.class);
        QueryWrap<SmsSendStatus> wrap = Wraps.q(null, params.getExtra(), getEntityClass());
        SmsSendStatusPageQuery pageQuery = params.getModel();
        wrap.lambda()
                .eq(SmsSendStatus::getTaskId, pageQuery.getTaskId())
                .like(SmsSendStatus::getTelNum, pageQuery.getTelNum())
                .like(SmsSendStatus::getBizId, pageQuery.getBizId())
                .like(SmsSendStatus::getExt, pageQuery.getExt())
                .like(SmsSendStatus::getCode, pageQuery.getCode())
                .like(SmsSendStatus::getMessage, pageQuery.getMessage())
                .eq(SmsSendStatus::getFee, pageQuery.getFee())
                .in(SmsSendStatus::getSendStatus, pageQuery.getSendStatus());
        getBaseService().page(page, wrap);
        return page;
    }
}
