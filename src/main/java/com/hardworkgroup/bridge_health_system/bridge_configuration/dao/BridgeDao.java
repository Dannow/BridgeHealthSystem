package com.hardworkgroup.bridge_health_system.bridge_configuration.dao;


import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
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
public interface BridgeDao extends Mapper<Bridge> {

    List<Bridge> selectAllBridge();

    void insertByKey(Bridge bridge);

    Bridge getBridgeByID(String bridgeID);

    void updateByKey(Bridge tempBridge);

    void deleteByKey(String bridgeID);

    // 获得桥梁下的所有传感器
    public Bridge getSensorByBridgeID(String bridgeID);
}