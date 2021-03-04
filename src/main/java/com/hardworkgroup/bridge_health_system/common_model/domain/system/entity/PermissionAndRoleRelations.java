package com.hardworkgroup.bridge_health_system.common_model.domain.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: hyl
 * @date: 2020/05/25
 **/
@Entity
@Table(name = "role_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionAndRoleRelations implements Serializable {

    /*@Id
    private String id;*/

    private String roleID;

    private String permissionID;




}
