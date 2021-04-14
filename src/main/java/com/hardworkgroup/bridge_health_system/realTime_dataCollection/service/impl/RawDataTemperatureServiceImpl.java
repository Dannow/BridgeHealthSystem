package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao.RawDataTemperatureDao;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RawDataTemperatureServiceImpl implements RawDataTemperatureService {
    @Autowired
    private RawDataTemperatureDao rawDataTemperatureDao;

    // 新增数据
    public void insertTemperatureData(RawDataTemperature rawDataTemperature){
        rawDataTemperatureDao.insertTemperatureData(rawDataTemperature);
    }

    // 根据传感器ID查询批量实时数据
    public List<RawDataTemperature> selectBatchRawDataTemperatureBySensorID(int sensorID, int number){
        return rawDataTemperatureDao.selectBatchRawDataTemperatureBySensorID(sensorID, number);
    }

    // 根据传感器ID和时间查询实时数据
    public List<RawDataTemperature> selectRawDataTemperatureBySensorIDAndTime(int sensorID, String targetTime){
        return rawDataTemperatureDao.selectRawDataTemperatureBySensorIDAndTime(sensorID,targetTime);
    }
}
