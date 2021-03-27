package com.hardworkgroup.bridge_health_system.data_analysis.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorCorrelationDataResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorMonitorDataResult;

import java.util.Date;
import java.util.List;

public interface SensorDataService {
    // 根据ID获取传感器数据
    public List<SensorCorrelationDataResult> getSensorDataBySensorID(String fileName, String sensorName, int dataNumber) throws Exception;

    // 根据日期获得某个传感器数据
    public List<SensorCorrelationDataResult> getSensorDataBySensorTime(String fileName, String sensorName, Date targetTime) throws Exception;

    // 通过传感器类型获得传感器数据
    public List<SensorMonitorDataResult> getSensorDataInSensorType(String fileName, List<String> sensorName, int dataNumber) throws Exception;

    // 通过传感器类型和日期获得传感器数据
    public List<SensorMonitorDataResult> getSensorDataInSensorTypeByTime(String fileName, List<String> sensorName, Date targetTime) throws Exception;
}
