package top.tangyh.basic.security.shiro;

import org.apache.shiro.SecurityUtils;
import top.tangyh.basic.security.model.SysUser;

/*
 * Created by Knight-ZXW on 2022/4/23
 * email: nimdanoob@163.com
 */
public class ShiroUtil {

    public static long getLoginUserId(){
        return ((SysUser) SecurityUtils.getSubject().getPrincipal()).getId();
    }
}
