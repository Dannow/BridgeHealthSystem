package com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawDataSmokeDao {
    // 新增数据
    public void insertRawDataSmoke(RawDataSmoke rawDataSmoke);

    // 根据传感器ID查询批量实时数据
    public List<RawDataSmoke> selectBatchRawDataSmokeBySensorID(@Param("sensorID") int sensorID, @Param("number") int number);

    // 根据传感器ID和时间查询实时数据
    public List<RawDataSmoke> selectRawDataSmokeBySensorIDAndTime(@Param("sensorID") int sensorID, @Param("targetTime") String targetTime);
}
