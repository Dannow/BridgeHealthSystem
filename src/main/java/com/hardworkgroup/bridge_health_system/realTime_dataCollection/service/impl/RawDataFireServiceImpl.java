package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl;

import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao.RawDataFireDao;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataFireService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataSmokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawDataFireServiceImpl implements RawDataFireService {
    @Autowired
    private RawDataFireDao rawDataFireDao;

    //新增数据
    public void insertRawDataFire(RawDataFire rawDataFire){
        rawDataFireDao.insertRawDataFire(rawDataFire);
    }
}
