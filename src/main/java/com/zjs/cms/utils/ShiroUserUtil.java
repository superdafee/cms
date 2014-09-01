package com.zjs.cms.utils;

import com.zjs.cms.service.account.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

/**
 * shiroUser公共类
 * @author mayufeng
 *
 */
public class ShiroUserUtil
{
    /**
     * 取出Shiro中的当前用户.
     */
    public static ShiroDbRealm.ShiroUser getCurrentUser() {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 验证是否包含其中一种权限
     * @param roles
     */
    public static void checkAnyRoles(String... roles){
        if(roles!=null && roles.length>0){
            Subject subject = SecurityUtils.getSubject();
            for(String role : roles) {
                if(subject.hasRole(role)){
                    return ;
                }
            }
        }
        throw new UnauthorizedException("not authorized!");
    }
}
