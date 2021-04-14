package com.hardworkgroup.bridge_health_system.common_model.domain.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * ID
     */
    @Id
    private Integer userID;

    private Integer bridgeID;
    /**
     * 用户名称
     */
    //@ExcelAttribute(sort = 1)
    private String userName;
    /**
     * 手机号码
     */
    //@ExcelAttribute(sort = 2)
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
    //@ExcelAttribute(sort = 6)
    private String departmentId;

    *//**
     * 入职时间
     *//*
    //@ExcelAttribute(sort = 5)
    private Date timeOfEntry;

    *//**
     * 聘用形式
     *//*
    //@ExcelAttribute(sort = 4)
    private Integer formOfEmployment;

    *//**
     * 工号
     *//*
    //@ExcelAttribute(sort = 3)
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

    private String departmentName;*/

    /**
     * level
     *     String
     *          saasAdmin：saas管理员具备所有权限
     *          coAdmin：企业管理（创建租户企业的时候添加）
     *          user：普通用户（需要分配角色）
     */
    private String userLevel;

    private String userPicture;

    private Set<Role> roles =new HashSet<Role>();


}
