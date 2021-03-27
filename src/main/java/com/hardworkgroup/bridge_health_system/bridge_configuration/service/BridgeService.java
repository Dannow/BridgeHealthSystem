package com.hardworkgroup.bridge_health_system.bridge_configuration.service;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;

import java.util.List;

/**
 * (BridgeService)表服务接口
 *
 * @author makejava
 * @since 2020-12-27 10:40:03
 */
//@Service
public interface BridgeService {


    PageInfo<Bridge> findAll(int pageNum, int pageSize);

    void save(Bridge bridge);

    Bridge getSensorByID(String id);

    void update(String id, Bridge bridge);

    void delete(String id);

    // 获得桥梁下的所有传感器
    public Bridge getSensorByBridgeID(String bridgeID);
}