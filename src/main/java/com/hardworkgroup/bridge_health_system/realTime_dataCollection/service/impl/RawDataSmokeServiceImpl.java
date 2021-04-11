package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao.RawDataSmokeDao;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataSmokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawDataSmokeServiceImpl implements RawDataSmokeService {
    @Autowired
    private RawDataSmokeDao rawDataSmokeDao;

    // 新增数据
    public void insertRawDataSmoke(RawDataSmoke rawDataSmoke){
        rawDataSmokeDao.insertRawDataSmoke(rawDataSmoke);
    }
}
