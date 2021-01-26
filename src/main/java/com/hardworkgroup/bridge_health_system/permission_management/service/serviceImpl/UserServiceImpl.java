package com.hardworkgroup.bridge_health_system.permission_management.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.common_model.domain.permission_management.User;
import com.hardworkgroup.bridge_health_system.permission_management.dao.UserDao;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    // 根据ID获取用户
    public User getUserByID(int userID){
        return userDao.getUserByID(userID);
    }
}
