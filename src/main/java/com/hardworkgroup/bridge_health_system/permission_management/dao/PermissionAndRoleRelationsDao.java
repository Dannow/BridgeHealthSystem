package com.hardworkgroup.bridge_health_system.permission_management.dao;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.PermissionAndRoleRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: hyl
 * @date: 2020/05/25
 **/

@org.apache.ibatis.annotations.Mapper
@Repository
public interface PermissionAndRoleRelationsDao extends Mapper<RoleAndUserRelations> {


    /**
     * 根据用户id查询对应的角色id
     * @param userID
     * @return
     */
    List<PermissionAndRoleRelations> findByRoleID(String userID);

    void updateByRoleID(PermissionAndRoleRelations target);

    void insertByRoleID(PermissionAndRoleRelations target);
}
