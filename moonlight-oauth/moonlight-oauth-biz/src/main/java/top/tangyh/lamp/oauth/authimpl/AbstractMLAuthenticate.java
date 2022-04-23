package top.tangyh.lamp.oauth.authimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.tangyh.basic.base.R;
import top.tangyh.basic.exception.code.ExceptionCode;
import top.tangyh.basic.security.auth.MoonLightAuthToken;
import top.tangyh.basic.security.auth.MoonLightAuthenticate;
import top.tangyh.basic.security.model.SysUser;
import top.tangyh.basic.utils.DateUtils;
import top.tangyh.basic.utils.SpringUtils;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.common.properties.SystemProperties;
import top.tangyh.lamp.oauth.event.LoginEvent;
import top.tangyh.lamp.oauth.event.model.LoginStatusDTO;

import java.time.LocalDateTime;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractMLAuthenticate implements MoonLightAuthenticate {
    protected final UserService userService;
    protected final SystemProperties systemProperties;


    @Override
    public abstract String authenticateType();

    @Override
    public abstract R<SysUser> authenticate(MoonLightAuthToken moonLightAuthToken);


    protected R<SysUser> authenticatePassword(String account, String password) {
        User user = this.userService.getByAccount(account);
        // 密码错误
        if (user == null) {
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }

        // 方便开发、测试、演示环境 开发者登录别人的账号，生产环境禁用。
        if (!systemProperties.getVerifyPassword()) {
            return R.success(userConvert(user));
        }

        String passwordMd5 = SecureUtil.sha256(password + user.getSalt());
        if (!passwordMd5.equalsIgnoreCase(user.getPassword())) {
            String msg = "用户名或密码错误!";
            // 密码错误事件
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.pwdError(user.getId(), msg)));
            return R.fail(msg);
        }

        // 密码过期
        if (user.getPasswordExpireTime() != null
                && LocalDateTime.now().isAfter(user.getPasswordExpireTime())) {
            String msg = "用户密码已过期，请修改密码或者联系管理员重置!";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        if (!user.getState()) {
            String msg = "用户被禁用，请联系管理员！";
            SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
            return R.fail(msg);
        }

        // 用户锁定
        Integer maxPasswordErrorNum = systemProperties.getMaxPasswordErrorNum();
        Integer passwordErrorNum = Convert.toInt(user.getPasswordErrorNum(), 0);
        if (maxPasswordErrorNum > 0 && passwordErrorNum >= maxPasswordErrorNum) {
            log.info("[{}][{}], 输错密码次数：{}, 最大限制次数:{}", user.getName(), user.getId(), passwordErrorNum, maxPasswordErrorNum);

            LocalDateTime passwordErrorLockTime = DateUtils.conversionDateTime(systemProperties.getPasswordErrorLockUserTime());
            log.info("passwordErrorLockTime={}", passwordErrorLockTime);
            if (passwordErrorLockTime.isAfter(user.getPasswordErrorLastTime())) {
                // 登录失败事件
                String msg = StrUtil.format("密码连续输错次数已达到{}次,用户已被锁定~", maxPasswordErrorNum);
                SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(user.getId(), msg)));
                return R.fail(msg);
            }
        }
        return R.success(userConvert(user));
    }

    protected SysUser userConvert(User user){
        SysUser sysUser = BeanUtil.toBean(user, SysUser.class);
        return sysUser;
    }
}
