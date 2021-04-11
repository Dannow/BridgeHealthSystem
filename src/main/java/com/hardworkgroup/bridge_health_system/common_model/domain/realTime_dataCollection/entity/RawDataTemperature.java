package com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/*
温度原始数据
 */
@Entity
@Table(name = "rawData_Temperature")
@Data
@NoArgsConstructor
public class RawDataTemperature {
    private int ID;
    // 采集时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date acquisitionTime;
    // 温度值
    private Float temperatureValue;
    // 原始数据对应的传感器
    private Sensor sensor;

    public RawDataTemperature(Date acquisitionTime, Float temperatureValue, Sensor sensor) {
        this.acquisitionTime = acquisitionTime;
        this.temperatureValue = temperatureValue;
        this.sensor = sensor;
    }
}
