package top.tangyh.basic.security.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
public class MoonLightAuthToken extends UsernamePasswordToken {

    private String loginType;

    public MoonLightAuthToken(String username,
                              String password,
                              String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }
}
