package com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response;

import lombok.Data;

import java.util.List;

@Data
public class SensorMonitorDataWithNameResult {
    //传感器名集合
    private List<String> sensorNameList;
    // 传感器数据
    private List<SensorMonitorDataResult> sensorMonitorDataResults;

    public SensorMonitorDataWithNameResult(List<String> sensorNameList ,List<SensorMonitorDataResult> sensorMonitorDataResults) {
        this.sensorMonitorDataResults = sensorMonitorDataResults;
        this.sensorNameList = sensorNameList;
    }
}
