package top.tangyh.lamp.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.tangyh.basic.annotation.base.IgnoreResponseBodyAdvice;
import top.tangyh.basic.base.R;
import top.tangyh.basic.exception.BizException;
import top.tangyh.basic.jwt.TokenUtil;
import top.tangyh.basic.jwt.model.AuthInfo;
import top.tangyh.basic.security.auth.MoonLightAuthToken;
import top.tangyh.lamp.authority.dto.auth.LoginParamDTO;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.OnlineService;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.oauth.granter.TokenGranterBuilder;
import top.tangyh.lamp.oauth.service.ValidateCodeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证Controller
 *
 * @author zuihou
 * @date 2020年03月31日10:10:36
 */
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "登录接口")
public class OauthController {

    private final ValidateCodeService validateCodeService;
    private final TokenGranterBuilder tokenGranterBuilder;
    private final TokenUtil tokenUtil;
    private final OnlineService onlineService;

    private final UserService userService;


    /**
     * 租户登录 lamp-ui 系统
     */
    @ApiOperation(value = "登录接口", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/login")
    public R<AuthInfo> login(@Validated @RequestBody LoginParamDTO login) throws BizException {
        return tokenGranterBuilder.getGranter(login.getGrantType()).grant(login);
    }

    /**
     * 租户登录 lamp-ui 系统
     */
    @ApiOperation(value = "登录接口2", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/login2")
    public R<User> login2(@Validated @RequestBody LoginParamDTO login) throws BizException {
        String account = login.getAccount();
        String password = login.getPassword();
        String grantType = login.getGrantType();
        try {
            MoonLightAuthToken token = new MoonLightAuthToken(account, password, grantType);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        }catch (AuthenticationException e){
            return R.fail("登录失败");
        }
        return R.success(userService.getByAccount(account));
    }

    /**
     * 租户登录 lamp-ui 系统
     */
    @ApiOperation(value = "测试接口", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/test")
    @RequiresPermissions("authority:menu:add")
    public R test(@Validated @RequestBody LoginParamDTO login) throws BizException {
        return R.success();
    }



    @ApiOperation(value = "退出", notes = "退出")
    @PostMapping(value = "/logout")
    public R<Boolean> logout(String token, Long userId, String clientId) {
        return R.success(onlineService.clear(token, userId, clientId));
    }

    /**
     * 验证验证码
     *
     * @param key  验证码唯一uuid key
     * @param code 验证码
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @GetMapping(value = "/anno/check")
    public R<Boolean> check(@RequestParam(value = "key") String key, @RequestParam(value = "code") String code) throws BizException {
        return this.validateCodeService.check(key, code);
    }

    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping(value = "/anno/captcha", produces = "image/png")
    @IgnoreResponseBodyAdvice
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        this.validateCodeService.create(key, response);
    }

    /**
     * 验证token
     */
    @ApiOperation(value = "验证token", notes = "验证token")
    @GetMapping(value = "/anno/verify")
    public R<AuthInfo> verify(@RequestParam(value = "token") String token) throws BizException {
        return R.success(tokenUtil.getAuthInfo(token));
    }


}
