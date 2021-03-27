package com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date originalDataCollectionTime;
    // 原始数据存放路径
    private String originalDataPath;
}
