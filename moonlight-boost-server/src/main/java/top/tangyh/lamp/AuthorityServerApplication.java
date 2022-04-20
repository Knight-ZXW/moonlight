package top.tangyh.lamp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import top.tangyh.basic.security.annotation.EnableLoginArgResolver;
import top.tangyh.basic.validator.annotation.EnableFormValidator;
import top.tangyh.lamp.common.properties.IgnoreProperties;
import top.tangyh.lamp.common.properties.SystemProperties;

import java.net.UnknownHostException;

import static top.tangyh.lamp.common.constant.BizConstant.*;

/**
 * https://mp.weixin.qq.com/s/zkxI5IQP0jFTjVYe5pTsXw
 * EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true) 配合 @EnableCaching
 * 才能解决在同一个类中通过 AopContext.currentProxy() 调用时，使缓存生效
 *
 * @author zuihou
 * @date 2018-01-13 1:34
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableDiscoveryClient
@Configuration
@ComponentScan({
        MOONLIGHT_BUSINESS_PACKAGE,UTIL_PACKAGE, BUSINESS_PACKAGE
})
@EnableFeignClients(value = {
        MOONLIGHT_BUSINESS_PACKAGE,UTIL_PACKAGE, BUSINESS_PACKAGE
})
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableLoginArgResolver
@EnableFormValidator
@EnableConfigurationProperties({IgnoreProperties.class, SystemProperties.class})
public class AuthorityServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AuthorityServerApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}/doc.html\n\t" +
                        "数据库监控: \t\thttp://{}:{}/druid\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                "127.0.0.1",
                env.getProperty("server.port"),
                "127.0.0.1",
                env.getProperty("server.port"));
    }
}
