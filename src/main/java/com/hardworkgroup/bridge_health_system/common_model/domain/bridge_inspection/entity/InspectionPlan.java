package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * (inspectionPlan)实体类
 *
 * @author hui
 * @since 2022-03-06 18:10:36
 */
@Entity
@Table(name = "inspection_plan")
@Data
@NoArgsConstructor
public class InspectionPlan {

    @Id
    private Integer inspectionPlanID;

    private Integer userID;

    private Integer bridgeID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionEndTime;

    private Integer inspectionCompletionStatus;

    private Integer inspectionCheckInStatus;

}