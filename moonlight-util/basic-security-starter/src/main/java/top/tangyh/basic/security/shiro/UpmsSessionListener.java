package top.tangyh.basic.security.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
@Slf4j
public class UpmsSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        log.debug(session+" onStart");

    }

    @Override
    public void onStop(Session session) {
        log.debug(session+" onStop");

    }

    @Override
    public void onExpiration(Session session) {
        log.debug(session+" onExpiration");
    }
}
