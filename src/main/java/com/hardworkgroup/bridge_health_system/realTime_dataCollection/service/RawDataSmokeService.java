package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RawDataSmokeService {
    // 新增数据
    public void insertRawDataSmoke(RawDataSmoke rawDataSmoke);

    // 根据传感器ID查询批量实时数据
    public List<RawDataSmoke> selectBatchRawDataSmokeBySensorID(int sensorID, int number);

    // 根据传感器ID和时间查询实时数据
    public List<RawDataSmoke> selectRawDataSmokeBySensorIDAndTime(int sensorID, String targetTime);
}
