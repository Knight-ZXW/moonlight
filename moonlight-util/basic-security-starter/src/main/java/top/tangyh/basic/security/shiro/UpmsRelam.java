package top.tangyh.basic.security.shiro;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.tangyh.basic.base.R;
import top.tangyh.basic.security.auth.MoonLightAuthenticate;
import top.tangyh.basic.security.auth.MoonLightAuthToken;
import top.tangyh.basic.security.feign.UserQuery;
import top.tangyh.basic.security.feign.UserResolverService;
import top.tangyh.basic.security.model.SysUser;

import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
public class UpmsRelam extends AuthorizingRealm {

    @Autowired
    UserResolverService userResolverService;


    /**
     * 调用 subject.login 时 会调用该函数
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        R<SysUser> userResponse = userResolverService.getById(Long.valueOf(username),
                new UserQuery());
        SysUser user = userResponse.getData();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //角色

        //权限

        return simpleAuthorizationInfo;
    }


    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MoonLightAuthToken moonLightAuthToken = (MoonLightAuthToken) token;

        Map<String, MoonLightAuthenticate> loginAuths = SpringUtil.getBeansOfType(MoonLightAuthenticate.class);
        String loginType = moonLightAuthToken.getLoginType();
        MoonLightAuthenticate loginAuth = loginAuths.get(loginType);
        if (loginAuth == null) {
            throw new AuthenticationException(loginType + " 未注册实现");
        }

        R<SysUser> r = loginAuth.authenticate(moonLightAuthToken);


        if (r.getIsSuccess() && r.getData() != null) {
            Long id = r.getData().getId();
            return new SimpleAuthenticationInfo(
                    id,
                    token.getCredentials(),
                    "UpmsRelam");
        }

        throw new MoonAuthenticationException(r.getCode(), r.getMsg());

    }

}
