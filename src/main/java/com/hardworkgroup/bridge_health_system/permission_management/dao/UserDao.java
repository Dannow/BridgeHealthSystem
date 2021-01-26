package com.hardworkgroup.bridge_health_system.permission_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.permission_management.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    // 根据ID获取用户
    public User getUserByID(int userID);
}
