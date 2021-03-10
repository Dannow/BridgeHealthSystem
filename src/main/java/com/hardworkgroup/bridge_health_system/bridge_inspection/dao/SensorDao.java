package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;



import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.Sensor;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TDimSensor)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-27 14:13:52
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface SensorDao extends Mapper<Sensor> {
    List<Sensor> getallTDimSensor();

    List<Sensor> selectAllSensor();

    void insertByKey(Sensor sensor);

    Sensor getSensorByID(String sensorID);

    void updateByKey(Sensor tempSensor);

    void deleteByKey(String sensorID);
}