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
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAndUserRelations implements Serializable {

    private String userID;

    private String roleID;


}
