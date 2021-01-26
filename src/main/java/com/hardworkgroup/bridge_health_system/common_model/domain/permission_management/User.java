package com.hardworkgroup.bridge_health_system.common_model.domain.permission_management;

import lombok.Data;
/*
* 用户类
* */
@Data
public class User {
    // 用户ID
    private int userID;
    // 用户名
    private String userName;
    // 用户电话
    private String userPhone;
    // 用户密码
    private String userPassword;
    // 用户邮箱
    private String userEmail;
    // 用户启用状态。0：停用；1：启用
    private int userStatus;
}
