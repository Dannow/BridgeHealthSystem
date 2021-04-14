package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * (CheckIn)实体类
 *
 * @author hui
 * @since 2022-03-26 18:08:32
 */
@Entity
@Table(name = "checkIn")
@Data
@NoArgsConstructor
public class CheckIn {

    @Id
    private Integer checkID;

    private Integer userID;

    private Integer inspectionPlanID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    private String checkLocation;

    private String checkPicture;

    private Integer checkStatus;

    @OneToOne
    private User user;

    @OneToOne
    private InspectionRecord inspectionRecord;
}