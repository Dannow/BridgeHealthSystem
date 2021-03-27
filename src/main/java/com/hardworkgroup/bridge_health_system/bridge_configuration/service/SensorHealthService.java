package com.hardworkgroup.bridge_health_system.bridge_configuration.service;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.SensorHealth;

/**
 * (SensorHealthService)表服务接口
 *
 * @author makejava
 * @since 2021-03-27 10:40:03
 */
public interface SensorHealthService {

    PageInfo<SensorHealth> findAll(int pageNum, int pageSize);

    PageInfo<SensorHealth> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize);

    void save(SensorHealth sensorHealth);

    SensorHealth getSensorByID(String sensorHealthID);

    void update(SensorHealth sensorHealth);

    void delete(String sensorHealthID);
}
