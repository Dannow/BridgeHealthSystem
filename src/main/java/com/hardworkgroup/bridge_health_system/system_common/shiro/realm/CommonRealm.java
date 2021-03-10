package com.hardworkgroup.bridge_health_system.system_common.shiro.realm;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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
        Set<String> apisPerms = (Set<String>) result.getRoles().get("points");
        //放置菜单权限
        apisPerms.add((String) result.getRoles().get("menus"));
        //构造权限信息,返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apisPerms);
        return info;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
