package com.hardworkgroup.bridge_health_system.bridge_configuration.service;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;

import java.util.List;

/**
 * (TDimSensor)表服务接口
 *
 * @author makejava
 * @since 2020-12-27 10:40:03
 */
//@Service
public interface SensorService {

    PageInfo<Sensor> findAll(int pageNum, int pageSize);

    void save(Sensor sensor);

    Sensor getSensorByID(String sensorID);

    void update(String sensorID, Sensor sensor);

    void delete(String sensorID);

    // 获得传感器对应的数据
    public Sensor getSensorOriginalData(String sensorID);
}