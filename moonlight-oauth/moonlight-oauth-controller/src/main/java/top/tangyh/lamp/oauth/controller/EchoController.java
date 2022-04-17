package top.tangyh.lamp.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.tangyh.basic.annotation.base.IgnoreResponseBodyAdvice;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.authority.service.common.DictionaryService;
import top.tangyh.lamp.authority.service.core.OrgService;
import top.tangyh.lamp.authority.service.core.StationService;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 数据注入的查询实现类
 *
 * @author zuihou
 * @date 2020/9/25 9:15 上午
 */
@Slf4j
@RestController
@AllArgsConstructor
@IgnoreResponseBodyAdvice
@Api(value = "数据注入查询接口", tags = "数据注入查询接口， 不建议前端调用")
@ApiIgnore
public class EchoController {
    private final DictionaryService dictionaryService;
    private final OrgService orgService;
    private final StationService stationService;
    private final UserService userService;


    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @GetMapping("/user/findByIds")
    public Map<Serializable, Object> findUserByIds(@RequestParam(value = "ids") Set<Serializable> ids) {
        return userService.findByIds(ids);
    }

    @GetMapping("/station/findByIds")
    public Map<Serializable, Object> findStationByIds(@RequestParam("ids") Set<Serializable> ids) {
        return stationService.findByIds(ids);
    }

    @GetMapping("/org/findByIds")
    public Map<Serializable, Object> findOrgByIds(@RequestParam("ids") Set<Serializable> ids) {
        return orgService.findByIds(ids);
    }

    @ApiOperation(value = "查询字典项", notes = "根据字典编码查询字典项")
    @GetMapping("/dictionary/findByIds")
    public Map<Serializable, Object> findDictByIds(@RequestParam("ids") Set<Serializable> ids) {
        return this.dictionaryService.findByIds(ids);
    }
}
