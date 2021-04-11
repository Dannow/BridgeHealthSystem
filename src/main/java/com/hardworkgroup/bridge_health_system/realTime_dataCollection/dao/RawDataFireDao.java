package com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import org.springframework.stereotype.Repository;

@Repository
public interface RawDataFireDao {
    //新增数据
    public void insertRawDataFire(RawDataFire rawDataFire);
}
