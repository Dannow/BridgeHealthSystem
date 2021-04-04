package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 桥梁实体类
 */
@Entity
@Table(name = "bridge")
@Data
@NoArgsConstructor
public class Bridge implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * ID
     */
    @Id
    private Integer bridgeID;
    /**
     * 桥梁名称
     */
    private String bridgeName;
    /**
     * 桥梁类型
     */
    private String bridgeType;
    /**
     * 桥梁长度
     */
    private Integer bridgeLength;
    /**
     * 桥梁最大速度
     */
    private Integer bridgeMaxSpeed;
    /**
     * 桥梁地址
     */
    private String bridgeAddress;
    /**
     * 桥梁照片
     */
    private String bridgePicture;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 维度
     */
    private String dimension;
    /**
     * 可用年限
     */
    private Integer availableYear;
    /**
     * 施工公司
     */
    private String constructionCompany;
    /**
     * 施工日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date constructionDate;

    private Double healthState;

    /**
     * 桥梁传感器
     */
    private Set<Sensor> sensors = new HashSet<>();

}
