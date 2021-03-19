package com.hardworkgroup.bridge_health_system.data_analysis.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.data_analysis.response.SensorDataResult;

import java.util.Date;
import java.util.List;

public interface SensorDataService {
    // 根据ID获取传感器数据
    public List<SensorDataResult> getSensorDataBySensorID(String fileName, String sensorName, int dataNumber) throws Exception;

    // 根据日期获得某个传感器数据
    public List<SensorDataResult> getSensorDataBySensorTime(String fileName, String sensorName, Date targetTime) throws Exception;
}
