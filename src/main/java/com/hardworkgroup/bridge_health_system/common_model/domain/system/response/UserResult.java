package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.RoleAndUserRelations;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResult implements Serializable {

    /**
     * ID
     */
    private String userID;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 手机号码
     */
    private String userPhone;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer userStatus;
    /**
     * 创建时间
     *//*
    private Date createTime;

    private String companyId;

    private String companyName;

    *//**
     * 部门ID
     *//*
    private String departmentId;

    *//**
     * 入职时间
     *//*
    private Date timeOfEntry;

    *//**
     * 聘用形式
     *//*
    private Integer formOfEmployment;

    *//**
     * 工号
     *//*
    private String workNumber;

    *//**
     * 管理形式
     *//*
    private String formOfManagement;

    *//**
     * 工作城市
     *//*
    private String workingCity;

    *//**
     * 转正时间
     *//*
    private Date correctionTime;

    *//**
     * 在职状态 1.在职  2.离职
     *//*
    private Integer inServiceStatus;

    private String departmentName;

    private String staffPhoto;  //用户头像*/

    private List<String> roleIds = new ArrayList<>();

    public UserResult(User user) {
        BeanUtils.copyProperties(user,this);
        for (Role roles : user.getRoles()) {
            this.roleIds.add(roles.getRoleID());
        }
    }
}
