package top.tangyh.lamp.authority.controller.auth;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.tangyh.basic.annotation.log.SysLog;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.controller.SuperController;
import top.tangyh.basic.base.entity.SuperEntity;
import top.tangyh.basic.base.request.PageParams;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.utils.BeanPlusUtil;
import top.tangyh.basic.utils.StrHelper;
import top.tangyh.lamp.authority.dto.auth.GlobalUserPageDTO;
import top.tangyh.lamp.authority.dto.auth.GlobalUserSaveDTO;
import top.tangyh.lamp.authority.dto.auth.GlobalUserUpdateDTO;
import top.tangyh.lamp.authority.dto.auth.UserUpdatePasswordDTO;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.common.constant.BizConstant;

import java.util.List;

import static top.tangyh.lamp.common.constant.SwaggerConstants.DATA_TYPE_STRING;
import static top.tangyh.lamp.common.constant.SwaggerConstants.PARAM_TYPE_QUERY;

/**
 * <p>
 * 前端控制器
 * 全局账号
 * </p>
 *
 * @author zuihou
 * @date 2019-10-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/globalUser")
@Api(value = "GlobalUser", tags = "全局账号")
@SysLog(enabled = false)
@RequiredArgsConstructor
public class GlobalUserController extends SuperController<UserService, Long, User, GlobalUserPageDTO, GlobalUserSaveDTO, GlobalUserUpdateDTO> {

    @Override
    public R<User> handlerSave(GlobalUserSaveDTO model) {
        User user = BeanPlusUtil.toBean(model, User.class);
        user.setName(StrHelper.getOrDef(model.getName(), model.getAccount()));
        if (StrUtil.isEmpty(user.getPassword())) {
            user.setPassword(BizConstant.DEF_PASSWORD);
        }
        user.setState(true);
        baseService.initUser(user);
        return success(user);
    }

    @Override
    public R<User> handlerUpdate(GlobalUserUpdateDTO model) {
        User user = BeanPlusUtil.toBean(model, User.class);
        baseService.updateUser(user);
        return success(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", dataType = DATA_TYPE_STRING, paramType = PARAM_TYPE_QUERY),
    })
    @ApiOperation(value = "检测账号是否可用", notes = "检测账号是否可用")
    @GetMapping("/check")
    public R<Boolean> check(@RequestParam String account) {
        return success(baseService.check(null, account));
    }

    @Override
    public IPage<User> query(PageParams<GlobalUserPageDTO> params) {
        IPage<User> page = params.buildPage(User.class);
        GlobalUserPageDTO model = params.getModel();

        baseService.pageByRole(page, params);

        page.getRecords().forEach(item -> {
            item.setPassword(null);
            item.setSalt(null);
        });
        return page;
    }


    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids[]", value = "主键id", dataType = DATA_TYPE_STRING, allowMultiple = true, paramType = PARAM_TYPE_QUERY),
    })
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        return success(baseService.remove(ids));
    }


    /**
     * 修改密码
     *
     * @param model 修改实体
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PutMapping("/reset")
    public R<Boolean> updatePassword(@RequestBody @Validated(SuperEntity.Update.class) UserUpdatePasswordDTO model) {
        return success(baseService.reset(model));
    }
}
