package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SimpleData {
    Integer inspectionDataID;

    Integer inspectionRecordID;

    Integer sensorID;

    Double manualSensorData;

    Integer status;

    String sensorName;

    public SimpleData(InspectionData inspectionData) {
        this.inspectionDataID = inspectionData.getInspectionDataID();
        this.inspectionRecordID = inspectionData.getInspectionRecordID();
        this.sensorID = inspectionData.getSensorID();
        this.manualSensorData = inspectionData.getManualSensorData();
        this.status = inspectionData.getStatus();
        this.sensorName = inspectionData.getSensor().getSensorName();
    }
}
