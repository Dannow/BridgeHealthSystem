package com.hardworkgroup.bridge_health_system.bridge_configuration.dao;


import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TDimSensor)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-27 14:13:52
 */
@Repository
public interface BridgeDao extends Mapper<Bridge> {

    List<Bridge> selectAllBridge();

    void insertByKey(Bridge bridge);

    Bridge getBridgeByID(String bridgeID);

    void updateByKey(Bridge tempBridge);

    void deleteByKey(String bridgeID);
}