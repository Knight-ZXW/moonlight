package top.tangyh.lamp.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.request.PageParams;
import top.tangyh.basic.base.service.SuperServiceImpl;
import top.tangyh.basic.context.ContextConstants;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.exception.BizException;
import top.tangyh.basic.jackson.JsonUtil;
import top.tangyh.basic.model.Kv;
import top.tangyh.basic.utils.ArgumentAssert;
import top.tangyh.basic.utils.BeanPlusUtil;
import top.tangyh.basic.utils.StrPool;
import top.tangyh.lamp.common.api.JobApi;
import top.tangyh.lamp.common.constant.JobConstant;
import top.tangyh.lamp.common.dto.XxlJobInfoVO;
import top.tangyh.lamp.sms.dao.SmsTaskMapper;
import top.tangyh.lamp.sms.dto.SmsTaskPageQuery;
import top.tangyh.lamp.sms.dto.SmsTaskSaveDTO;
import top.tangyh.lamp.sms.dto.SmsTaskUpdateDTO;
import top.tangyh.lamp.sms.entity.SmsSendStatus;
import top.tangyh.lamp.sms.entity.SmsTask;
import top.tangyh.lamp.sms.entity.SmsTemplate;
import top.tangyh.lamp.sms.enumeration.SendStatus;
import top.tangyh.lamp.sms.enumeration.SourceType;
import top.tangyh.lamp.sms.enumeration.TaskStatus;
import top.tangyh.lamp.sms.service.SmsSendStatusService;
import top.tangyh.lamp.sms.service.SmsTaskService;
import top.tangyh.lamp.sms.service.SmsTemplateService;
import top.tangyh.lamp.sms.strategy.SmsContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static top.tangyh.basic.exception.code.ExceptionCode.BASE_VALID_PARAM;

/**
 * <p>
 * ???????????????
 * ????????????
 * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * ??????????????????????????????????????????#sms_send_status??????
 * </p>
 *
 * @author zuihou
 * @date 2019-08-01
 */
@Slf4j
@Service

@RequiredArgsConstructor
public class SmsTaskServiceImpl extends SuperServiceImpl<SmsTaskMapper, SmsTask> implements SmsTaskService {
    private final JobApi jobApi;
    private final SmsContext smsContext;
    private final SmsTemplateService smsTemplateService;
    private final SmsSendStatusService smsSendStatusService;

    private static String processTemplate(String template, String regex, List<Kv> params) {
        log.info("regex={}, template={}, params={}", regex, template, params);
        if (StrUtil.isEmpty(template)) {
            return StrPool.EMPTY;
        }
        if (CollUtil.isEmpty(params)) {
            return StrPool.EMPTY;
        }

        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile(regex).matcher(template);
        int index = 0;
        while (m.find()) {
            String value = "";
            if (index + 1 <= params.size()) {
                Kv kv = params.get(index);
                value = kv.getValue();
            }
            value = value == null ? "" : value;
            if (value.contains("$")) {
                value = value.replaceAll("\\$", "\\\\\\$");
            }
            m.appendReplacement(sb, value);
            index++;
        }
        m.appendTail(sb);
        if (index < params.size()) {
            throw BizException.wrap("?????????????????????????????????: {} ???????????????????????????: {}", params.size(), index);
        } else if (index > params.size()) {
            throw BizException.wrap("?????????????????????????????????: {} ???????????????????????????: {}", params.size(), index);
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        List<Kv> list = new ArrayList<>();
//        list.add(Kv.builder().key("$var").value("atg").build());
//        list.add(Kv.builder().key("$var").value("1234").build());
//        list.add(Kv.builder().key("$var").value("01").build());
//        list.add(Kv.builder().key("$var").value("5").build());
//        list.add(Kv.builder().key("$var").value("5").build());
//        processTemplate("{$var}????????????????????????{$var}(??????{$var})??????????????????{$var}?????????", ProviderType.CL.getRegex(), list);
////        processTemplate("?????? ${xx} ??????????????????", ProviderType.ALI.getRegex(), list);
//    }

    @Override
    @Transactional(readOnly = true)
    public IPage<SmsTask> pageSmsTask(IPage<SmsTask> page, PageParams<SmsTaskPageQuery> query) {
        return baseMapper.pageSmsTask(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsTask saveTask(SmsTaskSaveDTO data) {
        if (data.getSourceType() == null) {
            data.setSourceType(SourceType.APP);
        }
        validAndInit(data);

        SmsTask smsTask = BeanPlusUtil.toBean(data, SmsTask.class);
        smsTask.setTemplateParams(JsonUtil.toJson(data.getTemplateParam()));
        send(smsTask, (task) -> {
            this.save(task);
            List<SmsSendStatus> list = data.getTelNum().stream().map(telNum -> {
                SmsSendStatus sss = new SmsSendStatus();
                sss.setTaskId(task.getId());
                sss.setSendStatus(SendStatus.WAITING);
                sss.setTelNum(telNum);
                return sss;
            }).collect(Collectors.toList());
            smsSendStatusService.saveBatch(list);
            return true;
        });
        return smsTask;
    }

    /**
     * ?????????????????????????????????
     */
    public void validAndInit(SmsTaskSaveDTO smsTask) {
        SmsTemplate template;

        template = smsTemplateService.getById(smsTask.getTemplateId());
        ArgumentAssert.notNull(template, "??????????????????????????????");


        //1?????????????????????
        ArgumentAssert.notEmpty(smsTask.getTelNum(), "????????????????????????");

        // ?????????????????????????????????????????????????????????+5????????? ?????????????????? ??????????????????????????????????????????
        if (smsTask.getSendTime() != null) {
            boolean flag = LocalDateTime.now().plusMinutes(4).isBefore(smsTask.getSendTime());
            ArgumentAssert.isTrue(flag, "??????????????????????????????????????????5????????????");
        }

        if (StrUtil.isEmpty(smsTask.getContent())) {
            smsTask.setContent(processTemplate(template.getContent(), template.getProviderType().getRegex(), smsTask.getTemplateParam()));
        } else if (smsTask.getContent().length() > 500) {
            throw new BizException(BASE_VALID_PARAM.getCode(), "????????????????????????500???");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsTask update(SmsTaskUpdateDTO data) {
        SmsTaskSaveDTO validDTO = BeanPlusUtil.toBean(data, SmsTaskSaveDTO.class);
        validAndInit(validDTO);

        SmsTask smsTask = BeanPlusUtil.toBean(validDTO, SmsTask.class);
        smsTask.setId(data.getId());
        smsTask.setSourceType(SourceType.APP);
        smsTask.setTemplateParams(data.getTemplateParam().toString());

        send(smsTask, (task) -> {
            updateById(task);
            if (task.getSendTime() == null) {
                update(Wraps.<SmsTask>lbU()
                        .set(SmsTask::getSendTime, null)
                        .eq(SmsTask::getId, task.getId()));
            }
            smsSendStatusService.remove(Wraps.<SmsSendStatus>lbQ().eq(SmsSendStatus::getTaskId, task.getId()));
            List<SmsSendStatus> list = data.getTelNum().stream().map(telNum -> {
                SmsSendStatus sss = new SmsSendStatus();
                sss.setTaskId(task.getId());
                sss.setSendStatus(SendStatus.WAITING);
                sss.setTelNum(telNum);
                return sss;
            }).collect(Collectors.toList());
            smsSendStatusService.saveBatch(list);
            return true;
        });
        return smsTask;
    }


    /**
     * ?????????????????????????????????
     */
    private SmsTask send(SmsTask smsTask, Function<SmsTask, Boolean> function) {
        //1??? ?????????????????????
        smsTask.setStatus(TaskStatus.WAITING);

        //2?????????or?????? ????????????
        if (!function.apply(smsTask)) {
            return smsTask;
        }

        //???????????????????????????
        if (smsTask.getDraft()) {
            return smsTask;
        }

        //3, ????????????????????????
        if (smsTask.getSendTime() == null) {
            smsContext.smsSend(smsTask.getId());
        } else {
            JSONObject param = new JSONObject();
            param.set("id", smsTask.getId());
            param.set(ContextConstants.JWT_KEY_TENANT, ContextUtil.getTenant());
            //??????????????????
            R<String> r = jobApi.addTimingTask(
                    XxlJobInfoVO.create(JobConstant.DEF_EXTEND_JOB_GROUP_NAME,
                            smsTask.getTopic(),
                            smsTask.getSendTime(),
                            JobConstant.SMS_SEND_JOB_HANDLER,
                            param.toString()));
            if (!r.getIsSuccess()) {
                throw BizException.wrap("??????????????????");
            }
        }
        return smsTask;
    }
}
