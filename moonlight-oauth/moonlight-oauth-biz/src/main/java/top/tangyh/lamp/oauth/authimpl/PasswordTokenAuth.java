package top.tangyh.lamp.oauth.authimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.tangyh.basic.base.R;
import top.tangyh.basic.security.auth.MoonLightAuthToken;
import top.tangyh.basic.security.model.SysUser;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.common.properties.SystemProperties;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */


//TODO  改为配置化注册，而不是 依赖 Component
@Component("AccountPassword")
@Slf4j
public class PasswordTokenAuth extends AbstractMLAuthenticate {

    public PasswordTokenAuth(UserService userService, SystemProperties systemProperties) {
        super(userService, systemProperties);
    }


    @Override
    public String authenticateType() {
        return "AccountPassword";
    }

    @Override
    public R<SysUser> authenticate(MoonLightAuthToken moonLightAuthToken){
        String username = moonLightAuthToken.getUsername();
        String password = new String(moonLightAuthToken.getPassword());

        User user = userService.getByAccount(username);
        if (user ==null){
            return R.fail("账号密码验证错误");
        }
        return authenticatePassword(username,password);

    }


}
