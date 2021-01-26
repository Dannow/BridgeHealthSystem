package com.hardworkgroup.bridge_health_system.permission_management.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.permission_management.User;

import java.util.List;

public interface UserService {
    // 根据ID获取用户
    public User getUserByID(int userID);
}
