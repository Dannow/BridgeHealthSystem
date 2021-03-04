package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleResult implements Serializable {
    private String roleID;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 说明
     */
    private String roleDescription;
    /**
     * 企业id
     *//*
    private String companyId;*/

    private List<String> permIds = new ArrayList<>();

    public RoleResult(Role role) {
        BeanUtils.copyProperties(role,this);
        for (Permission perm : role.getPermissions()) {
            //不返回所有权限信息，仅返回权限ID
            this.permIds.add(perm.getPermissionID());
        }
    }
}
