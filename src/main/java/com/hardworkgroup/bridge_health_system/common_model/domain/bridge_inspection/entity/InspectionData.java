package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * (inspectionPlan)实体类
 *
 * @author hui
 * @since 2022-03-06 18:10:36
 */
@Entity
@Table(name = "inspection_data")
@Data
@NoArgsConstructor
public class InspectionData {

    @Id
    Integer inspectionDataID;

    Integer inspectionRecordID;

    Integer sensorID;

    Double manualSensorData;

    Integer status;

    @ManyToOne
    private Sensor sensor;
}
