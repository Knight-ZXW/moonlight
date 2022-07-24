package top.tangyh.lamp.common.cache.common;


import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.common.cache.CacheKeyDefinition;

import java.time.Duration;

/**
 * 参数 KEY
 * <p>
 *
 * @author zuihou
 * @date 2020/9/20 6:45 下午
 */
public class CaptchaCacheKeyBuilder implements CacheKeyBuilder {
    @Override
    public String getPrefix() {
        return CacheKeyDefinition.CAPTCHA;
    }



    @Override
    public Duration getExpire() {
        return Duration.ofMinutes(15);
    }
}
