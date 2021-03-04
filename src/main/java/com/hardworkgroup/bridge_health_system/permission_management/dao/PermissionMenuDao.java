package com.hardworkgroup.bridge_health_system.permission_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionMenu;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
  * 企业数据访问接口
  */

@org.apache.ibatis.annotations.Mapper
@Repository
public interface PermissionMenuDao extends Mapper<PermissionMenu> {

    List<PermissionMenu> findAll();

    PermissionMenu getPermissionMenuByID(String id);

    void deletePermissionMenuByKey(String id);

    void insertPermissionMenu(PermissionMenu menu);

    void updatePermissionMenuByKey(PermissionMenu menu);
}