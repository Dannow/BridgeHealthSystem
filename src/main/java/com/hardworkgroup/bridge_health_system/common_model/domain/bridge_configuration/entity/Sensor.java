package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity.OriginalData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
public class Sensor implements Serializable {

    @Id
    private Integer sensorID;

    private Integer bridgeID;

    private String sensorName;

    private String sensorLocation;

    private String sensorType;

    private Integer upperThreshold;

    private Integer lowerThreshold;

    private Integer sensorStatus;

    private Integer isShort;

    // 传感器对应的原始数据
    @OneToMany
    private Set<OriginalData> originalDatas = new HashSet<>();

    @ManyToOne
    private Bridge bridge;

}