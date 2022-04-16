package top.tangyh.basic.log.event;


import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.log.entity.OptLogDTO;

import java.util.function.Consumer;


/**
 * 异步监听日志事件
 *
 * @author zuihou
 * @date 2019-07-01 15:13
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final Consumer<OptLogDTO> consumer;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        OptLogDTO sysLog = (OptLogDTO) event.getSource();

        // 非租户模式 (NONE) ， 需要修改这里的判断
        if (sysLog == null) {
            return;
        }

        consumer.accept(sysLog);
    }

}
