package com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RawDataTemperatureDao {
    // 新增数据
    public void insertTemperatureData(RawDataTemperature rawDataTemperature);

    // 根据传感器ID查询批量实时数据
    public List<RawDataTemperature> selectBatchRawDataTemperatureBySensorID(@Param("sensorID") int sensorID, @Param("number") int number);


}
