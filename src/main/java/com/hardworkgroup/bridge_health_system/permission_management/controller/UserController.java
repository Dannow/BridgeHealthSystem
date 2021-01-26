package com.hardworkgroup.bridge_health_system.permission_management.controller;

import com.hardworkgroup.bridge_health_system.common_model.domain.permission_management.User;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissionManagement")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping("/getUser/{userID}")
    public Result getUser(@PathVariable("userID") int userID){
        // 获得所有用户
        User user = userService.getUserByID(userID);
        return new Result(ResultCode.SUCCESS,user);
    }
}
