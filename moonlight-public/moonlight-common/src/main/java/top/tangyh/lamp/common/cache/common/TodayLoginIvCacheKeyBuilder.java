package top.tangyh.lamp.common.cache.common;


import top.tangyh.basic.cache.model.CacheKey;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.common.cache.CacheKeyDefinition;

import java.time.Duration;
import java.time.LocalDate;

/**
 * 参数 KEY
 * TODAY_LOGIN_IV:{now} -> long
 * <p>
 * #c_login_log
 *
 * @author zuihou
 * @date 2020/9/20 6:45 下午
 */
public class TodayLoginIvCacheKeyBuilder implements CacheKeyBuilder {
    public static CacheKey build(LocalDate now) {
        return new TodayLoginIvCacheKeyBuilder().key(now.toString());
    }

    @Override
    public String getPrefix() {
        return CacheKeyDefinition.TODAY_LOGIN_IV;
    }

    @Override
    public Duration getExpire() {
        return Duration.ofDays(2L);
    }
}
