package com.hardworkgroup.bridge_health_system.common_model.domain.project_overview.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
public class SingleSensorDataResult {
    // 传感器ID
    private Integer sensorID;
    // 传感器名称
    private String sensorName;
    // 传感器位置
    private String sensorLocation;
    // 传感器类型
    private String sensorType;
    // 传感器上限阀值
    private Integer upperThreshold;
    // 传感器下限阀值
    private Integer lowerThreshold;
    // 传感器数据
    private float sensorData;
    // 读取的行数
    private int dataIndex;
}
