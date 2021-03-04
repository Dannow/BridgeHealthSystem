package com.hardworkgroup.bridge_health_system.permission_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionPoint;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
  * 企业数据访问接口
  */

@org.apache.ibatis.annotations.Mapper
@Repository
public interface PermissionPointDao extends Mapper<PermissionPoint> {

    PermissionPoint getPermissionPointByID(String id);

    void deletePermissionPointByKey(String id);

    void insertPermissionPoint(PermissionPoint point);

    void updatePermissionPointByKey(PermissionPoint point);
}