package top.tangyh.lamp.authority.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.request.PageParams;
import top.tangyh.basic.base.request.PageUtil;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKey;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.database.mybatis.conditions.query.LbqWrapper;
import top.tangyh.basic.security.feign.UserQuery;
import top.tangyh.basic.security.model.SysRole;
import top.tangyh.basic.security.model.SysUser;
import top.tangyh.basic.utils.ArgumentAssert;
import top.tangyh.basic.utils.BeanPlusUtil;
import top.tangyh.basic.utils.CollHelper;
import top.tangyh.basic.utils.StrPool;
import top.tangyh.lamp.authority.dao.auth.UserMapper;
import top.tangyh.lamp.authority.dto.auth.GlobalUserPageDTO;
import top.tangyh.lamp.authority.dto.auth.ResourceQueryDTO;
import top.tangyh.lamp.authority.dto.auth.UserUpdateAvatarDTO;
import top.tangyh.lamp.authority.dto.auth.UserUpdatePasswordDTO;
import top.tangyh.lamp.authority.entity.auth.Resource;
import top.tangyh.lamp.authority.entity.auth.Role;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.entity.auth.UserRole;
import top.tangyh.lamp.authority.service.auth.ResourceService;
import top.tangyh.lamp.authority.service.auth.RoleService;
import top.tangyh.lamp.authority.service.auth.UserRoleService;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.common.cache.auth.*;
import top.tangyh.lamp.common.constant.BizConstant;
import top.tangyh.lamp.file.service.AppendixService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static top.tangyh.lamp.common.constant.BizConstant.DEF_PASSWORD;


/**
 * <p>
 * 业务实现类
 * 账号
 * </p>
 *
 * @author zuihou
 * @date 2019-07-03
 */
@Slf4j
@Service

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl extends SuperCacheServiceImpl<UserMapper, User> implements UserService {

    private final RoleService roleService;
    private final ResourceService resourceService;
    private final UserRoleService userRoleService;
    private final AppendixService appendixService;

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new UserCacheKeyBuilder();
    }

    @Override
    public IPage<User> pageByRole(IPage<User> page, PageParams<GlobalUserPageDTO> params) {
        params.put("roleCode", BizConstant.INIT_ROLE_CODE);
        PageUtil.timeRange(params);
        return baseMapper.pageByRole(page, params);
    }

    @Override
    public IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper) {
        return baseMapper.findPage(page, wrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(UserUpdatePasswordDTO data) {
        ArgumentAssert.notEmpty(data.getOldPassword(), "当前密码不能为空");
        User user = getById(data.getId());
        ArgumentAssert.notNull(user, "用户不存在");
        ArgumentAssert.equals(user.getId(), ContextUtil.getUserId(), "只能修改自己的密码");
        String oldPassword = SecureUtil.sha256(data.getOldPassword() + user.getSalt());
        ArgumentAssert.equals(user.getPassword(), oldPassword, "旧密码错误");

        return reset(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset(UserUpdatePasswordDTO data) {
        ArgumentAssert.equals(data.getConfirmPassword(), data.getPassword(), "密码和重复密码不一致");
        User user = getById(data.getId());
        ArgumentAssert.notNull(user, "用户不存在");
        String defPassword = SecureUtil.sha256(data.getPassword() + user.getSalt());
        super.update(Wraps.<User>lbU()
                .set(User::getPassword, defPassword)
                .set(User::getPasswordErrorNum, 0L)
                // 置空
                .set(User::getPasswordErrorLastTime, null)
                .eq(User::getId, data.getId())
        );
        delCache(data.getId());
        return true;
    }

    @Override
    public User getByAccount(String account) {
        Function<CacheKey, Object> loader = new Function<CacheKey, Object>() {
            @Override
            public Object apply(CacheKey k) {
                return UserServiceImpl.this.getObj(Wraps.<User>lbQ().select(User::getId).eq(User::getAccount, account), Convert::toLong);
            }
        };
        CacheKeyBuilder builder = new UserAccountCacheKeyBuilder();
        return getByKey(builder.key(account), loader);
    }

    @Override
    public List<User> findUserByRoleId(Long roleId, String keyword) {
        return baseMapper.findUserByRoleId(roleId, keyword);
    }

    @Override
    public Map<String, Object> getDataScopeById(Long userId) {
        Map<String, Object> map = new HashMap<>(4);
        List<Long> orgIds = new ArrayList<>();

        List<Role> list = roleService.findRoleByUserId(userId);

        // 找到 dsType 最大的角色， dsType越大，角色拥有的权限最大
//        Optional<Role> max = list.stream().max(Comparator.comparingInt(item -> item.getDsType().getVal()));

//        DataScopeType dsType;
//        if (max.isPresent()) {
//            Role role = max.get();
//            dsType = role.getDsType();
//            map.put("dsType", dsType.getVal());
//            if (DataScopeType.CUSTOMIZE.eq(dsType)) {
//                LbqWrapper<RoleOrg> wrapper = Wraps.<RoleOrg>lbQ().select(RoleOrg::getOrgId).eq(RoleOrg::getRoleId, role.getId());
//                List<RoleOrg> roleOrgList = roleOrgService.list(wrapper);
//
//                orgIds = roleOrgList.stream().mapToLong(RoleOrg::getOrgId).boxed().collect(Collectors.toList());
//            } else if (DataScopeType.THIS_LEVEL.eq(dsType)) {
//                User user = getByIdCache(userId);
//                if (user != null) {
//                    Long orgId = user.getOrgId();
//                    if (orgId != null) {
//                        orgIds.add(orgId);
//                    }
//                }
//            } else if (DataScopeType.THIS_LEVEL_CHILDREN.eq(dsType)) {
//                User user = getByIdCache(userId);
//                if (user != null) {
//                    Long orgId = user.getOrgId();
//                    List<Org> orgList = orgService.findChildren(CollUtil.newArrayList(orgId));
//                    orgIds = orgList.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList());
//                }
//            }
//        }
        map.put("orgIds", orgIds);
        return map;
    }

    @Override
    public boolean check(Long id, String account) {
        //这里不能用缓存，否则会导致用户无法登录
        return count(
                Wraps.<User>lbQ()
                .eq(User::getAccount, account)
                        .ne(User::getId, id)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrPasswordErrorNumById(Long id) {
        baseMapper.incrPasswordErrorNumById(id);
        delCache(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPassErrorNum(Long id) {
        int count = baseMapper.resetPassErrorNum(id, LocalDateTime.now());
        delCache(id);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) {
        ArgumentAssert.isFalse(check(null, user.getAccount()),
                "账号{}已经存在", user.getAccount());

        ArgumentAssert.isFalse(check(null, user.getEmail()),
                "邮箱{}已经存在", user.getEmail());

        ArgumentAssert.isFalse(check(null, user.getMobile()),
                "手机号{}已经存在", user.getMobile());

        user.setSalt(RandomUtil.randomString(20));
        if (StrUtil.isEmpty(user.getPassword())) {
            user.setPassword(DEF_PASSWORD);
        }
        user.setPassword(SecureUtil.sha256(user.getPassword() + user.getSalt()));
        user.setPasswordErrorNum(0);
        super.save(user);
        return user;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        // 不允许修改用户信息时修改密码，请单独调用修改密码接口
        user.setPassword(null);
        user.setSalt(null);
        updateById(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        userRoleService.remove(Wraps.<UserRole>lbQ().in(UserRole::getUserId, ids));
        cacheOps.del(ids.stream().map(new UserRoleCacheKeyBuilder()::key).toArray(CacheKey[]::new));
        cacheOps.del(ids.stream().map(new UserMenuCacheKeyBuilder()::key).toArray(CacheKey[]::new));
        cacheOps.del(ids.stream().map(new UserResourceCacheKeyBuilder()::key).toArray(CacheKey[]::new));
        return removeByIds(ids);
    }

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return CollHelper.uniqueIndex(findUser(ids), User::getId, User::getName);
    }

    @Override
    public List<User> findUser(Set<Serializable> ids) {
        // 强转， 防止数据库隐式转换，  若你的id 是string类型，请勿强转
        return findByIds(ids,
                missIds -> super.listByIds(missIds.stream().filter(Objects::nonNull).map(Convert::toLong).collect(Collectors.toList()))
        );
    }

    @Override
    public List<User> findUserById(List<Long> ids) {
        return findUser(new HashSet<>(ids));
    }

    @Override
    public SysUser getSysUserById(Long id, UserQuery query) {
        User user = getByIdCache(id);
        if (user == null) {
            return null;
        }
        SysUser sysUser = BeanUtil.toBean(user, SysUser.class);

        if (query.getFull() || query.getRoles()) {
            List<Role> list = roleService.findRoleByUserId(id);
            sysUser.setRoles(BeanPlusUtil.toBeanList(list, SysRole.class));
        }
        if (query.getFull() || query.getResource()) {
            List<Resource> resourceList = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(id).build());
            sysUser.setResources(CollHelper.split(resourceList, Resource::getCode, StrPool.SEMICOLON));
        }

        return sysUser;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> findAllUserId() {
        return super.listObjs(Wraps.<User>lbQ().select(User::getId), Convert::toLong);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initUser(User user) {
        ArgumentAssert.isFalse(check(null, user.getAccount()), "账号{}已经存在", user.getAccount());
        this.saveUser(user);
        return userRoleService.initAdmin(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Long todayUserCount() {
        return count(Wraps.<User>lbQ().leFooter(User::getCreateTime, LocalDateTime.now()).geHeader(User::getCreateTime, LocalDateTime.now()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAvatar(UserUpdateAvatarDTO data) {
        ArgumentAssert.isFalse(StrUtil.isEmpty(data.getAvatar()) && data.getAppendixAvatar() == null, "请上传或选择头像");
        if (StrUtil.isNotEmpty(data.getAvatar())) {
            return updateById(User.builder().avatar(data.getAvatar()).id(data.getId()).build());
        }
        return appendixService.save(data.getId(), data.getAppendixAvatar());
    }
}
