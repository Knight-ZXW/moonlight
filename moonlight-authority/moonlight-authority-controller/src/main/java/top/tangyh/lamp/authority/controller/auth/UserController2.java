package top.tangyh.lamp.authority.controller.auth;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.annotation.security.PreAuth;
import top.tangyh.lamp.authority.dto.auth.UserSaveDTO;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.UserService;

/*
 * Created by Knight-ZXW on 2022/4/19
 * email: nimdanoob@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(value = "User", tags = "用户")
@PreAuth(replace = "authority:user:")
@RequiredArgsConstructor
public class UserController2 {

    @Autowired
    public UserService userService;

    @PostMapping("register")
    public void register(@RequestBody UserSaveDTO userSaveDto){
        User user = BeanUtil.toBean(userSaveDto, User.class);
        user.setReadonly(false);
        userService.saveUser(user);
    }


}
