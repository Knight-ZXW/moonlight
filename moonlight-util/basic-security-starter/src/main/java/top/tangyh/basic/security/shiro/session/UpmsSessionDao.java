package top.tangyh.basic.security.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
@Slf4j
public class UpmsSessionDao extends CachingSessionDAO {

    private final static String UPMS_SHIRO_SESSION_ID = "moonlight-upms-session-id";
    @Autowired
    RedisTemplate<String, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(UPMS_SHIRO_SESSION_ID + "_" + session.getId(), session,
                session.getTimeout() / 1000, TimeUnit.SECONDS);
        return null;
    }

    @Override
    protected void doUpdate(Session session) {
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());

        //过期强制退出
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }

        redisTemplate.opsForValue().set(UPMS_SHIRO_SESSION_ID + "_" + session.getId(), session,
                session.getTimeout() / 1000, TimeUnit.SECONDS);

    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(UPMS_SHIRO_SESSION_ID + "_" + session.getId());
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisTemplate.opsForValue().get(UPMS_SHIRO_SESSION_ID + "_" + sessionId);
    }


}
