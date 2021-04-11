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
烟雾传感器原始数据
 */
@Entity
@Table(name = "rawData_Smoke")
@Data
@NoArgsConstructor
public class RawDataSmoke {
    private int ID;
    // 采集时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date acquisitionTime;
    // 烟雾值
    private int smokeValue;
    // 对应的传感器
    private Sensor sensor;

    public RawDataSmoke(Date acquisitionTime, int smokeValue, Sensor sensor) {
        this.acquisitionTime = acquisitionTime;
        this.smokeValue = smokeValue;
        this.sensor = sensor;
    }
}
