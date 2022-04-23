package top.tangyh.basic.security.shiro;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.tangyh.basic.base.R;
import top.tangyh.basic.security.auth.MoonLightAuthToken;
import top.tangyh.basic.security.auth.MoonLightAuthenticate;
import top.tangyh.basic.security.feign.UserQuery;
import top.tangyh.basic.security.feign.UserResolverService;
import top.tangyh.basic.security.model.SysRole;
import top.tangyh.basic.security.model.SysUser;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Long userId = (Long) principals.getPrimaryPrincipal();
        R<SysUser> userResponse = userResolverService.getById(userId,
                new UserQuery().setFull(true));
        SysUser user = userResponse.getData();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        HashSet<String> permissions = new HashSet<>(user.getResources());
        List<String> roles = user.getRoles().stream().map(SysRole::getCode).collect(Collectors.toList());
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(new HashSet<>(roles));
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

        Map<String, MoonLightAuthenticate> loginAuths
                = SpringUtil.getBeansOfType(MoonLightAuthenticate.class);
        String loginType = moonLightAuthToken.getLoginType();
        MoonLightAuthenticate loginAuth = loginAuths.get(loginType);
        if (loginAuth == null) {
            throw new AuthenticationException(loginType + " 未注册实现");
        }
        R<SysUser> r = loginAuth.authenticate(moonLightAuthToken);

        //get salt , hash time => into AuthenticationInfo

        if (r.getIsSuccess() && r.getData() != null) {
            Long id = r.getData().getId();
            return new SimpleAuthenticationInfo(
                    r.getData(),
                    token.getCredentials(),
                    "UpmsRelam");
        }

        throw new MoonAuthenticationException(r.getCode(), r.getMsg());
    }

    @Override
    public Class getAuthenticationTokenClass() {
        return MoonLightAuthToken.class;
    }
}
