package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RawDataFireService {
    //新增数据
    public void insertRawDataFire(RawDataFire rawDataFire);

    // 根据传感器ID查询批量实时数据
    public List<RawDataFire> selectBatchRawDataFireBySensorID(int sensorID, int number);

    // 根据传感器ID和时间查询实时数据
    public List<RawDataFire> selectRawDataFireBySensorIDAndTime(int sensorID, String targetTime);
}
