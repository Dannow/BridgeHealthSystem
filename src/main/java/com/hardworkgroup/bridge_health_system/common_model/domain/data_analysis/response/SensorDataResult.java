package com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SensorDataResult {
    // 传感器数据的生成时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sensorDataTime;
    // 传感器数据
    private float sensorData;

    public SensorDataResult( Date sensorDataTime, float sensorData){
        this.sensorDataTime = sensorDataTime;
        this.sensorData = sensorData;
    }
}
