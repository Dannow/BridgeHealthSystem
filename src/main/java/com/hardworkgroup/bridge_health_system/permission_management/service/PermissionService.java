package com.hardworkgroup.bridge_health_system.permission_management.service;

import com.hardworkgroup.bridge_health_system.system_common.exception.CommonException;
import org.apache.shiro.authz.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    public void save(Map<String,Object> map) throws Exception;

    public void update(Map<String,Object> map) throws Exception;

    public Map findById(String id) throws CommonException;

    //public List<Permission> findAll(Map<String,Object> map);

    public void deleteById(String id) throws CommonException;

    //public List<Permission> getMenus();
}
