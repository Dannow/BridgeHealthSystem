package com.hardworkgroup.bridge_health_system.system_common.shiro.realm;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 公共的realm
 * @author: hyl
 * @date: 2020/02/08
 **/
public class CommonRealm extends AuthorizingRealm {

    public void setName(String name){
        super.setName("CommonRealm");
    }


    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取安全数据
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
        //获取权限信息
        Set<String> apisPerms = (Set<String>) result.getRoles().get("apis");
        Set<String> menusPerms = (Set<String>) result.getRoles().get("menus");
        Set<String> pointsPerms = (Set<String>) result.getRoles().get("points");
        Set<String> perms = new HashSet<>(apisPerms);
        perms.addAll(menusPerms);
        perms.addAll(pointsPerms);
        //构造权限信息,返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        return info;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
