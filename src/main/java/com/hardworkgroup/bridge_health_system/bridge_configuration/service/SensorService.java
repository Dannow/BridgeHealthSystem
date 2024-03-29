package com.hardworkgroup.bridge_health_system.bridge_configuration.service;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import org.apache.ibatis.annotations.Param;

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

    PageInfo<Sensor> findAll(Integer bridgeID, String sensorType, int pageNum, int pageSize);

    PageInfo<Sensor> findAll(Integer bridgeID, int pageNum, int pageSize);

    void save(Sensor sensor);

    Sensor getSensorByID(String sensorID);

    void update(Sensor sensor);

    void delete(String sensorID);

    // 获得传感器对应的数据
    public Sensor getSensorOriginalData(String sensorID);

    // 根据传感器类型获得传感器
    public List<Sensor> getSensorOriginalDataBySensorType(String sensorType, String bridgeID);

    // 根据传感器ID获取报警信息
    public Sensor getAlarmInformationBySensorID(int sensorID);

    // 根据传感器ID获取单位
    public String getUnitBySensorID(String sensorID);

    // 根据传感器类型获取传感器单位
    public String getUnitBySensorType(String sensorType);

    // 根据桥梁ID获取短期或长期传感器
    public List<Sensor> getLongOrShortSensorByBridgeID(String bridgeID, Integer isShort);
}