package com.hardworkgroup.bridge_health_system.permission_management.dao;



import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author: hyl
 * @date: 2020/01/15
 **/

@org.apache.ibatis.annotations.Mapper
@Repository
public interface RoleDao extends Mapper<Role> {
    void insertByKey(Role role);

    Role getRoleByID(String roleID);

    void updateByKey(Role target);


    void deleteByKey(String id);

    List<Role> selectAllRoles();

    Set<Role> getRoleByUserId(String userID);
}
