package com.hardworkgroup.bridge_health_system.permission_management.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;

import java.util.List;

public interface RoleService {

    void assignPerms(String roleId, List<String> permIds);

    public void save(Role role);

    public void update(Role role);

    public Role findById(String id);

    public void delete(String id);

    public List<Role> findAll(String companyId);

}
