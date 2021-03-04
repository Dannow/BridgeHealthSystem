package com.hardworkgroup.bridge_health_system.permission_management.dao;


import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: hyl
 * @date: 2020/05/25
 **/

@org.apache.ibatis.annotations.Mapper
@Repository
public interface RoleAndUserRelationsDao extends Mapper<RoleAndUserRelations> {


    /**
     * 根据用户id查询对应的角色id
     * @param userID
     * @return
     */
    List<RoleAndUserRelations> findByUserId(String userID);

    void updateByUserID(RoleAndUserRelations target);

    void insertByUserID(RoleAndUserRelations target);
}
