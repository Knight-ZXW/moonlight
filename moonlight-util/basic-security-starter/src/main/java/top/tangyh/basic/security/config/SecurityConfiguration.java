package top.tangyh.basic.security.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import top.tangyh.basic.security.properties.SecurityProperties;

/**
 * 权限认证配置类
 *
 * @author zuihou
 * @date 2020年03月29日22:34:45
 */
@Order
@AllArgsConstructor
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityConfiguration {
    private final SecurityProperties securityProperties;

//    @Bean
//    @ConditionalOnProperty(prefix = SecurityProperties.PREFIX,
//            name = "enabled",
//            havingValue = "true",
//            matchIfMissing = true)
//    public UriSecurityPreAuthAspect uriSecurityPreAuthAspect(VerifyAuthFunction verifyAuthFunction) {
//        return new UriSecurityPreAuthAspect(verifyAuthFunction);
//    }
//
//    @Bean("fun")
//    @ConditionalOnMissingBean(VerifyAuthFunction.class)
//    public VerifyAuthFunction getVerifyAuthFunction(UserResolverService userResolverService) {
//        return new VerifyAuthFunction(userResolverService, securityProperties);
//    }

}
