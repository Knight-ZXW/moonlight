package top.tangyh.basic.security.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.tangyh.basic.security.shiro.filter.UpmsAuthenticationFilter;
import top.tangyh.basic.security.shiro.session.UpmsSessionFactory;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
@Configuration
public class ShiroConfig {


    @Bean
    public RedisManager  shiroRedisManager(){
        return new RedisManager();
    }
    @Bean
    public CacheManager shiroCacheManager(RedisManager shiroRedisManager){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(shiroRedisManager);
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager shiroRedisManager){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(shiroRedisManager);
        return redisSessionDAO;
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager
    ){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainMap = new LinkedHashMap<>();
        //todo 测试
        filterChainMap.put("/apm/**", "anon");

        filterChainMap.put("/login", "anon");
        filterChainMap.put("/login2", "anon");

        filterChainMap.put("/user/login", "anon");
        filterChainMap.put("/user/register","anon");

        //swaager pass
        // 放行Swagger相关访问
        // swagger3
        filterChainMap.put("/swagger-ui/**", "anon");
        filterChainMap.put("/v3/**", "anon");
        filterChainMap.put("/swagger-resources/**", "anon");
        // knife4j
        filterChainMap.put("/doc.html", "anon");
        filterChainMap.put("/webjars/**", "anon");

        //druid pass
        filterChainMap.put("/druid","anon");
        // other user
        filterChainMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("authc",new UpmsAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    @Bean
    SecurityManager securityManager(@Qualifier("upmsRelam") UpmsRelam upmsRelam
    , CacheManager shiroCacheManager, SessionManager sessionManager){
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(upmsRelam);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(shiroCacheManager);

        // inject redisCacheManager
//        securityManager.setCacheManager(redisCacheManager);
        //todo 支持remember me 管理
//        securityManager.setRememberMeManager();
        return securityManager;
    }

    @Bean
    SessionManager sessionManager(SessionDAO redisSessionDao){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(new UpmsSessionDao());
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionFactory(sessionFactory());
        sessionManager.setSessionIdCookie(sessionIdCookie());

        //定期验证会话 是否过期，这里处于性能考虑，内部系统 不进行主动验证
        sessionManager.setSessionValidationSchedulerEnabled(false);
        ArrayList<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(new UpmsSessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        return sessionManager;
    }

    //创建自定义Realm
    @Bean
    public UpmsRelam upmsRelam() {
        UpmsRelam realm = new UpmsRelam();
        //开启认证缓存
        realm.setAuthenticationCachingEnabled(true);
        //开启授权缓存
        realm.setAuthorizationCachingEnabled(true);
        return realm;
    }

    @Bean
    SessionListener sessionListener(){
        return  new UpmsSessionListener();
    }

    @Bean
    SessionFactory sessionFactory(){
        return new UpmsSessionFactory();
    }


    @Bean
    public Cookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("JESESSION");
        //保证通过 js脚本无法读物cookie信息，防止xss攻击
        simpleCookie.setHttpOnly(true);
        //配置化 ms
        simpleCookie.setMaxAge(1000*60*60);
        return  simpleCookie;
    }


    //开启对shior注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }



}
