package com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import org.springframework.stereotype.Repository;

@Repository
public interface RawDataSmokeDao {
    // 新增数据
    public void insertRawDataSmoke(RawDataSmoke rawDataSmoke);
}
