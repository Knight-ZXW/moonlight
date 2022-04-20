package top.tangyh.basic.security.shiro;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationException;


/*
 * Created by Knight-ZXW on 2022/4/20
 * email: nimdanoob@163.com
 */

@Getter
public class MoonAuthenticationException extends AuthenticationException {

    private int errorCode;
    private String msg;
    public MoonAuthenticationException(int errorCode,String msg){
        this.errorCode = errorCode;
        this.msg =  msg;
    }
}
