package com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_flow")
@Data
@NoArgsConstructor
public class FlowInfo {
    @Id
    private Integer id;
    private String flowName;
    private String flowKey;
    private String filePath;
    /**
     * 1- 没有部署  0- 已经部署
     */
    private Integer state;
    private Date createTime;
}
