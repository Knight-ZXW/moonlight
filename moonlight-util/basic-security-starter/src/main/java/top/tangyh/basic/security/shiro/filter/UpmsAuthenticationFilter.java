package top.tangyh.basic.security.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import top.tangyh.basic.base.R;
import top.tangyh.basic.security.feign.UserResolverService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */
@Slf4j
public class UpmsAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserResolverService userResolverService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();

        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     *
     * @param request
     * @param response
     * @return true if the request should continue to be processed;
     * false if the subclass will handle/render the response directly.
     *
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //有SSO的需求，应该让前端实现自动跳转
        if (log.isTraceEnabled()){
            log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
        }
        R<Object> failed = R.fail(401, "未登录");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(objectMapper.writeValueAsString(failed));
        out.flush();
        out.close();
        return false;
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return super.executeLogin(request, response);
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HttpSession session = ((HttpServletRequest)request).getSession();
//        session.setAttribute(Const.CURRENT_USER, user);
        R<Boolean> success = R.success();
        String s = objectMapper.writeValueAsString(success);
        out.println(s);
        out.flush();
        out.close();
        return true;
    }

    @SneakyThrows
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        out.println(objectMapper.writeValueAsString(R.fail("登录失败")));
        out.flush();
        out.close();
        return false;
    }
}
