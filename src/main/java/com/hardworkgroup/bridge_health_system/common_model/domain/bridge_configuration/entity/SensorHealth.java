package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sensor_health")
@Data
@NoArgsConstructor
public class SensorHealth {
    /**
     * ID
     */
    @Id
    private Integer sensorHealthID;
    /**
     * 传感器类型
     */
    private String sensorType;
    /**
     * 桥梁Id
     */
    private Integer bridgeID;
    /**
     * 传感器分数
     */
    private Double sensorScore;
    /**
     * 传感器分数权重
     */
    private Double weight;

    @ManyToOne
    private Bridge bridge;
}
