package top.tangyh.basic.security.auth;

import top.tangyh.basic.base.R;
import top.tangyh.basic.security.model.SysUser;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
public interface MoonLightAuthenticate {
    /**
     * 登录类型
     *
     * @return
     */
    String authenticateType();


    R<SysUser> authenticate(MoonLightAuthToken moonLightAuthToken);
}
