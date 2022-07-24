package top.tangyh.lamp.common.cache.common;


import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.common.cache.CacheKeyDefinition;

/**
 * 参数 KEY
 * LOGIN_LOG_TEN_DAY -> long
 * <p>
 * #c_login_log
 *
 * @author zuihou
 * @date 2020/9/20 6:45 下午
 */
public class LoginLogTenDayCacheKeyBuilder implements CacheKeyBuilder {
    @Override
    public String getPrefix() {
        return CacheKeyDefinition.LOGIN_LOG_TEN_DAY;
    }

}
