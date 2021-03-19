package com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "original_data")
@Data
@NoArgsConstructor
public class OriginalData {
    // 原始数据ID
    private int originalDataID;
    // 原始数据采集时间
    private Date originalDataCollectionTime;
    // 原始数据存放路径
    private String originalDataPath;
}
