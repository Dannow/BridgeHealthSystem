package com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response;

import lombok.Data;

import java.util.Date;

@Data
public class SensorDataResult {
    // 传感器数据的生成时间
    private Date sensorDataTime;
    // 传感器数据
    private float sensorData;

    public SensorDataResult( Date sensorDataTime, float sensorData){
        this.sensorDataTime = sensorDataTime;
        this.sensorData = sensorData;
    }
}
