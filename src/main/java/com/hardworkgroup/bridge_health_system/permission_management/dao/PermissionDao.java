package com.hardworkgroup.bridge_health_system.permission_management.dao;




import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
  * 权限数据访问接口
  */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface PermissionDao extends Mapper<Permission> {

    List<Permission> findByTypeAndPid(int type, String pid);

    List<Permission> findByTypeAndEnVisible(int type, String enVisible);

    Permission getPermissionByID(String userID);

    void deletePermissionByKey(Permission permission);

    void insertPermission(Permission perm);

    void updatePermissionByKey(Permission permission);
}