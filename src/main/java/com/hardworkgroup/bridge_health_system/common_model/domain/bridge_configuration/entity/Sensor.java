package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * (TDimSensor)实体类
 *
 * @author xiaoG
 * @since 2020-12-27 14:13:08
 */
@Entity
@Table(name = "sensor")
@Data
@NoArgsConstructor
public class Sensor {

    @Id
    private Integer sensorID;

    private Integer bridgeID;

    private String sensorName;

    private String sensorLocation;

    private String sensorType;

    private Integer upperThreshold;

    private Integer lowerThreshold;

    private Integer sensorStatus;

}