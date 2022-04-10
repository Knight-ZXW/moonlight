package top.tangyh.basic.security.annotation;

import top.tangyh.basic.security.config.LoginArgResolverConfig;
import top.tangyh.basic.security.config.UserResolveFeignConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在启动类上添加该注解来----开启自动登录用户对象注入
 * Token转化SysUser
 *
 * @author zuihou
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({UserResolveFeignConfiguration.class, LoginArgResolverConfig.class})
public @interface EnableLoginArgResolver {
}
