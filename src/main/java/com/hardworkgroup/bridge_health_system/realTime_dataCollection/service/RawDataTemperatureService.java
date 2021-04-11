package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao.RawDataTemperatureDao;

import java.util.List;

public interface RawDataTemperatureService {
    // 新增数据
    public void insertTemperatureData(RawDataTemperature rawDataTemperature);

    // 根据传感器ID查询批量实时数据
    public List<RawDataTemperature> selectBatchRawDataTemperatureBySensorID(int sensorID, int number);
}
