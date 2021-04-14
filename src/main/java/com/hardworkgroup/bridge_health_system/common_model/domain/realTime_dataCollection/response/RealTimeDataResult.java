package com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class RealTimeDataResult {

    // 传感器ID
    private Integer sensorID;

    // 传感器名
    private String sensorName;

    // 传感器位置
    private String sensorLocation;

    // 传感器类型
    private String sensorType;

    // 传感器上限
    private Integer upperThreshold;

    // 传感器下限
    private Integer lowerThreshold;

    // 采集时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date acquisitionTime;

    // 实时值
    private Object realTimeValue;

    public RealTimeDataResult(Sensor sensor, Date acquisitionTime, Object realTimeValue) {
        this.sensorID = sensor.getSensorID();
        this.sensorName = sensor.getSensorName();
        this.sensorLocation = sensor.getSensorLocation();
        this.sensorType = sensor.getSensorType();
        this.upperThreshold = sensor.getUpperThreshold();
        this.lowerThreshold = sensor.getLowerThreshold();
        this.acquisitionTime = acquisitionTime;
        this.realTimeValue = realTimeValue;
    }
}
