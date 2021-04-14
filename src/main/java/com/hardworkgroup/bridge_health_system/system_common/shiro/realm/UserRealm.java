package com.hardworkgroup.bridge_health_system.system_common.shiro.realm;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.response.ProfileResult;
import com.hardworkgroup.bridge_health_system.permission_management.service.PermissionService;
import com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl.UserServiceImpl;
import com.hardworkgroup.bridge_health_system.system_common.Constants.Constant;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: hyl
 * @date: 2020/02/08
 **/
public class UserRealm extends CommonRealm {

    @Autowired
    private UserServiceImpl userService;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        //获取用户的手机号和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String phone = upToken.getUsername();
        String password = new String(upToken.getPassword());
        //根据手机号查询用户
        User user = userService.findByPhone(phone);
        //根据用户是否存在,用户密码是否和输入密码一致
        if (user != null && user.getUserPassword().equals(password)){
            //构造安全数据并返回(安全数据：用户基本信息,权限信息,ProfileResult)
            ProfileResult result = null;
            //如果是员工,就把员工的信息保存
            if (Constant.UserLevel.USER.equals(user.getUserLevel())){
                result = new ProfileResult(user);
            }else{
                Map map = new HashMap();
                //如果是企业管理员,就查询企业管理员可见的
                if (Constant.UserLevel.BRIDGE_ADMIN.equals(user.getUserLevel())){
                    map.put("userStatus" , "1");
                }else if (Constant.UserLevel.SUPER_ADMIN.equals(user.getUserLevel())){
                    //如果是SaaS管理员，只显示企业不显示的
                    /**
                     * 即只显示企业管理和模块管理
                     */
                    map.put("userStatus" , "0");
                }
                //List<Permission> list = permissionService.findAll(map);
                //result = new ProfileResult(user , list);
            }
            //构造方法：安全数据,密码,realm域名
            return new SimpleAuthenticationInfo(result , user.getUserPassword() , this.getName());
        }
        //返回null,会抛出异常,表示用户名和密码不匹配
        return null;
    }

    
}
