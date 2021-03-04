package com.hardworkgroup.bridge_health_system.permission_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionApi;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
  * 企业数据访问接口
  */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface PermissionApiDao extends Mapper<PermissionApi> {

    PermissionApi getPermissionApiByID(String id);

    void deletePermissionApiByKey(String id);

    void insertPermissionApi(PermissionApi api);

    void updatePermissionApiByKey(PermissionApi api);
}