package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
public class ProfileResult implements Serializable,AuthCachePrincipal {
    private String userId;
    private String phone;
    private String username;
    //private String company;
    //private String companyId;
    private Map<String,Object> roles = new HashMap<>();
//    private Set<Role> roles =new HashSet<>();
    /**
     *
     * @param user
     */
    public ProfileResult(User user, List<Permission> list) {
        this.phone = user.getUserPhone();
        this.username = user.getUserName();
        //this.company = user.getCompanyName();
        //this.companyId = user.getCompanyId();
        this.userId = user.getUserID();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        for (Permission perm : list) {
            String code = perm.getPermissionCode();
            if(perm.getPermissionType() == 1) {
                menus.add(code);
            }else if(perm.getPermissionType() == 2) {
                points.add(code);
            }else {
                apis.add(code);
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }


    public ProfileResult(User user) {
        this.phone = user.getUserPhone();
        this.username = user.getUserName();
        //this.company = user.getCompanyName();
        //this.companyId = user.getCompanyId();
        this.userId = user.getUserID();
//      Set<RoleAndUserRelations> roleAndUserRelations = user.getRauRelations();
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Role role : roles) {
            Set<Permission> perms = role.getPermissions();
            for (Permission perm : perms) {
                String code = perm.getPermissionCode();
                if(perm.getPermissionType() == 1) {
                    menus.add(code);
                }else if(perm.getPermissionType() == 2) {
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }

        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
