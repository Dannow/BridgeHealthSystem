package com.hardworkgroup.bridge_health_system.permission_management.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;

import java.util.Set;

public interface RoleAndUserRelationsService {

    void deleteRoleAndUserByID(String userID, String roleID);

    Set<Role> getRoleDetailByRoleId(Set<RoleAndUserRelations> roleByUserId);

    Set<RoleAndUserRelations> findRoleByUserId(String userId);
}
