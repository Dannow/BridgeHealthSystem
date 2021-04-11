package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

/**
 * (InspectionRecord)实体类
 *
 * @author hui
 * @since 2022-03-06 18:08:32
 */
@Entity
@Table(name = "inspection_record")
@Data
@NoArgsConstructor
public class InspectionRecord {

    @Id
    private Integer inspectionRecordID;

    private Integer inspectionPlanID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionTime;

    private String inspectionRecordTitle;

    private String inspectionContentDescription;

    @ManyToOne
    private InspectionPlan inspectionPlan;

    @OneToMany
    private List<InspectionData> inspectionData = new ArrayList<>();

    @OneToMany
    private List<ProblemEvent> problemEvents = new ArrayList<>();
}