package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResult implements Serializable {

    /**
     * ID
     */
    private String userID;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 手机号码
     */
    private String userPhone;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer userStatus;

    private Set<String> roleIds = new HashSet<>();

    public UserResult(User user) {
        BeanUtils.copyProperties(user,this);
        for (Role roles : user.getRoles()) {
            this.roleIds.add(roles.getRoleID());
        }
    }
}
