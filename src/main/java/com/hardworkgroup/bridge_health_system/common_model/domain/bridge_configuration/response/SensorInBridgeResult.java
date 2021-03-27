package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class SensorInBridgeResult {
    private Integer sensorID;

    private String sensorName;

    private String sensorLocation;

    private String sensorType;

    private Integer upperThreshold;

    private Integer lowerThreshold;

    private Integer sensorStatus;

    public SensorInBridgeResult(Sensor sensor) {
        this.sensorID = sensor.getSensorID();
        this.sensorName = sensor.getSensorName();
        this.sensorLocation = sensor.getSensorLocation();
        this.sensorType = sensor.getSensorType();
        this.upperThreshold = sensor.getUpperThreshold();
        this.lowerThreshold = sensor.getLowerThreshold();
        this.sensorStatus = sensor.getSensorStatus();
    }
}
