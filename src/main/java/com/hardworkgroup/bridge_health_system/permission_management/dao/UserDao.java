package com.hardworkgroup.bridge_health_system.permission_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
@Repository
public interface UserDao extends Mapper<User> {

    // 根据ID获取用户
    User getUserByID(String userID);

    User findByPhone(String phone);

    void updateByKey(User t);

    void deleteByKey(String id);

    void insertByKey(User t);

    List<User> selectAllUsers();
}
