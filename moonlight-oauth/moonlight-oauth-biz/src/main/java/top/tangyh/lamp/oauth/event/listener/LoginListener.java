package top.tangyh.lamp.oauth.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.tangyh.basic.database.properties.DatabaseProperties;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.authority.service.common.LoginLogService;
import top.tangyh.lamp.oauth.event.LoginEvent;
import top.tangyh.lamp.oauth.event.model.LoginStatusDTO;

/**
 * 登录事件监听，用于记录登录日志
 *
 * @author zuihou
 * @date 2020年03月18日17:39:59
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginListener {
    private final LoginLogService loginLogService;
    private final UserService userService;
    private final DatabaseProperties databaseProperties;

    @Async
    @EventListener({LoginEvent.class})
    public void saveSysLog(LoginEvent event) {
        LoginStatusDTO loginStatus = (LoginStatusDTO) event.getSource();
        log.info("loginStatus={}", loginStatus);


        if (LoginStatusDTO.Type.SUCCESS == loginStatus.getType()) {
            // 重置错误次数 和 最后登录时间
            this.userService.resetPassErrorNum(loginStatus.getId());

        } else if (LoginStatusDTO.Type.PWD_ERROR == loginStatus.getType()) {
            // 密码错误
            this.userService.incrPasswordErrorNumById(loginStatus.getId());
        }
        loginLogService.save(loginStatus.getId(), loginStatus.getAccount(), loginStatus.getUa(), loginStatus.getIp(), loginStatus.getLocation(), loginStatus.getDescription());
    }

}
