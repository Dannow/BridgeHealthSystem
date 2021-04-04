package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 用户实体类
 */
@Entity
@Table(name = "problem_event")
@Data
@NoArgsConstructor
public class ProblemEvent implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * 问题事件ID
     */
    @Id
    private Integer problemEventID;
    /**
     * 巡检记录ID
     */
    private Integer inspectionRecordID;
    /**
     * 传感器ID
     */
    private Integer sensorID;
    /**
     * 用户ID
     */
    private Integer userID;
    /**
     * 问题创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date problemCreationTime;
    /**
     * 维修状态
     */
    private String maintenanceStatus;
    /**
     * 确认状态
     */
    private String confirmStatus;
    /**
     * 问题事件标题
     */
    private String problemTitle;
    /**
     * 问题事件描述
     */
    private String problemDescription;

    @ManyToOne
    private InspectionRecord inspectionRecord;

    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    private User user;

    @OneToMany
    private List<ProblemEventPicture> problemEventPictures = new ArrayList<>();
}
