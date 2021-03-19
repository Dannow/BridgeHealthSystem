package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


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

    // 传感器对应的原始数据
    private Set<OriginalData> originalDatas = new HashSet<>();

}