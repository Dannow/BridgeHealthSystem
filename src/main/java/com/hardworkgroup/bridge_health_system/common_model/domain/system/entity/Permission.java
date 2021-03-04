package com.hardworkgroup.bridge_health_system.common_model.domain.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    @Id
    private String permissionID;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer permissionType;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限描述
     */
    private String permissionDescription;

    private String pid;

    //可见状态
    private String enVisible;

    public Permission(String name, Integer type, String code, String description) {
        this.permissionName = name;
        this.permissionType = type;
        this.permissionCode = code;
        this.permissionDescription = description;
    }


}