package com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface RawDataFireDao {
    //新增数据
    public void insertRawDataFire(RawDataFire rawDataFire);

    // 根据传感器ID查询批量实时数据
    public List<RawDataFire> selectBatchRawDataFireBySensorID(@Param("sensorID") int sensorID, @Param("number") int number);

    // 根据传感器ID和时间查询实时数据
    public List<RawDataFire> selectRawDataFireBySensorIDAndTime(@Param("sensorID") int sensorID, @Param("targetTime") String targetTime);
}
