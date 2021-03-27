package com.hardworkgroup.bridge_health_system.bridge_configuration.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.SensorHealth;
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
public interface SensorHealthDao extends Mapper<SensorHealth> {


    List<SensorHealth> selectAllSensorHealth();

    void insertByKey(SensorHealth sensorHealth);

    SensorHealth getSensorHealthByID(String sensorHealthID);

    void updateByKey(SensorHealth sensorHealth);

    void deleteByKey(String sensorHealthID);

    List<SensorHealth> selectAllByBridgeID(Integer bridgeID);
}