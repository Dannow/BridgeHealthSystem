package com.hardworkgroup.bridge_health_system.bridge_configuration.dao;



import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * (TDimSensor)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-27 14:13:52
 */
@Repository
public interface SensorDao extends Mapper<Sensor> {
    List<Sensor> getallTDimSensor();

    List<Sensor> selectAllSensor();

    List<Sensor> selectAllByBridgeIDAndSensorType(Integer bridgeID,String sensorType);

    void insertByKey(Sensor sensor);

    Sensor getSensorByID(String sensorID);

    void updateByKey(Sensor sensor);

    void deleteByKey(String sensorID);

    // 获得传感器对应的数据
    public Sensor getSensorOriginalData(String sensorID);

    // 根据传感器类型获得传感器
    public List<Sensor> getSensorOriginalDataBySensorType(@Param("sensorType") String sensorType, @Param("bridgeID") String bridgeID);

    // 根据传感器ID获取报警信息
    public Sensor getAlarmInformationBySensorID(int sensorID);

    // 根据传感器类型获取传感器单位
    public List<Sensor> getUnitBySensorType(String sensorType);

    List<Sensor> selectAllByBridgeID(Integer bridgeID);

    // 根据桥梁ID获取短期或长期传感器
    public List<Sensor> getLongOrShortSensorByBridgeID(@Param("bridgeID") String bridgeID, @Param("isShort") Integer isShort);
}