package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import lombok.Data;

import javax.persistence.Id;


/**
 * (TDimSensor)实体类
 *
 * @author xiaoG
 * @since 2020-12-27 14:13:08
 */
@Data
public class Sensor {

    @Id
    private Integer sensorId;

    private Integer bridgeID;

    private String sensorName;

    private String sensorLocation;

    private String sensorType;

    private Integer upperThreshold;

    private Integer lowerThreshold;

    private Integer sensorStatus;

}