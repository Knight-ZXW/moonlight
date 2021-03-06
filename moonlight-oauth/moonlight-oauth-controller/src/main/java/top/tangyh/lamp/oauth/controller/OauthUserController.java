package top.tangyh.lamp.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.tangyh.basic.annotation.base.IgnoreResponseBodyAdvice;
import top.tangyh.basic.base.R;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.security.feign.UserQuery;
import top.tangyh.basic.security.model.SysUser;
import top.tangyh.lamp.authority.service.auth.UserService;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 用户
 * </p>
 *
 * @author zuihou
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(value = "User", tags = "用户")
@AllArgsConstructor
@ApiIgnore
public class OauthUserController {
    private final UserService userService;

    //获取当前用户的详细信息
    /**
     * TODO 平台接口独立模块
     * 获取用户详细信息
     * @return 查询结果
     */
    @ApiOperation(value = "查询当前用户详细信息", notes = "查询当前用户详细信息")
    @GetMapping(value = "getUserInfo")
    public R<SysUser> getUserInfo(){
        Long userId = ContextUtil.getUserId();
        UserQuery userQuery = new UserQuery();
        userQuery.setFull(true);
        return R.success(userService.getSysUserById(userId,userQuery));
    }



    /**
     * 单体查询用户
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询用户详细", notes = "查询用户详细")
    @PostMapping(value = "/anno/id/{id}")
    public R<SysUser> getById(@PathVariable Long id, @RequestBody UserQuery query) {
        return R.success(userService.getSysUserById(id, query));
    }

    /**
     * 根据用户id，查询用户权限范围
     *
     * @param id 用户id
     */
    @ApiOperation(value = "查询用户权限范围", notes = "根据用户id，查询用户权限范围")
    @GetMapping(value = "/ds/{id}")
    @IgnoreResponseBodyAdvice
    public Map<String, Object> getDataScopeById(@PathVariable("id") Long id) {
        return userService.getDataScopeById(id);
    }



}
