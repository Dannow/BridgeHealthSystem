package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.permission_management.dao.RoleAndUserRelationsDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.RoleAndUserRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: hyl
 * @date: 2020/05/25
 **/
@Service
public class RoleAndUserRelationsServiceImpl implements RoleAndUserRelationsService {

    @Autowired
    private RoleAndUserRelationsDao RoleAndUserRelationsDao;

    @Autowired
    private RoleServiceImpl roleService;

    public Set<RoleAndUserRelations> findRoleByUserId(String userId){
        return RoleAndUserRelationsDao.findByUserId(userId);
    }


    public Set<Role> getRoleDetailByRoleId(Set<RoleAndUserRelations> roleByUserId) {
        Set<Role> res = new HashSet<>();
        for (RoleAndUserRelations userAndRoleRea : roleByUserId) {
            Role role = roleService.findById(userAndRoleRea.getRoleID());
            if (!ObjectUtils.isEmpty(role)){
                res.add(role);
            }
        }
        return res;
    }
}
