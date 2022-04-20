//package top.tangyh.basic.security.shiro;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.apache.shiro.session.Session;
//import top.tangyh.basic.cache.RedisAutoConfigure;
//import top.tangyh.basic.cache.repository.CachePlusOps;
//
//import java.util.ArrayList;
//import java.util.List;
//
///*
// * Created by Knight-ZXW on 2022/4/20
// * email: nimdanoob@163.com
// */
//@Component
//@AllArgsConstructor
//public class RedisManager {
//
//    private final RedisTemplate<String, Session> redisTemplate;
//
//    private static final String KEY = "shareSessionMap";
//
//    public void hadd(String sessionId, byte[] bytes){
//        redisTemplate.boundHashOps(KEY).put(sessionId, bytes);
//    }
//    public void hadd(String sessionId, Session session){
//        redisTemplate.boundHashOps(KEY).put(sessionId, session);
//    }
//
//    public void hdelete(String sessionId){
//        redisTemplate.boundHashOps(KEY).delete(sessionId);
//    }
//
//    public Session hget(String sessionId){
//        return (Session) redisTemplate.boundHashOps(KEY).get(sessionId);
//    }
//
//    public List<Session> hmget(){
//        List<Session> list = new ArrayList<>();
//
//        List<Object> values = redisTemplate.boundHashOps(KEY).values();
//        for (Object object : values) {
//            list.add((Session) object);
//        }
//        return list;
//    }
//}
