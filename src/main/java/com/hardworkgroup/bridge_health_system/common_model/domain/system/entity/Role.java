package com.hardworkgroup.bridge_health_system.common_model.domain.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pe_role")
@Getter
@Setter
public class Role implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    @Id
    private String roleID;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 说明
     */
    private String roleDescription;

    private Set<Permission> permissions = new HashSet<Permission>(0);//角色与模块  多对多
}