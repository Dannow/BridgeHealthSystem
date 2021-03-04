package com.hardworkgroup.bridge_health_system.permission_management.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;

import java.util.List;

public interface UserService {
    // 根据ID获取用户
    User getUserByID(String userID);

    User findByPhone(String mobile);

    void save(User user);

    void update(String id , User user);

    void deleteById(String id);

    void assignRoles(String userId, List<String> roleIds);

    void saveAll(List<User> list, String companyId, String companyName);


}
