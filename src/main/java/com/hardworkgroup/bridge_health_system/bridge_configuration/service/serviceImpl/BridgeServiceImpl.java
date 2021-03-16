package com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.dao.BridgeDao;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.BridgeService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (BridgeServiceImpl)表服务实现类
 *
 * @author makejava
 * @since 2020-12-27 10:47:29
 */
@Service
public class BridgeServiceImpl implements BridgeService {
    @Autowired
    private BridgeDao bridgeDao;


    @Override
    public PageInfo<Bridge> findAll(int pageNum, int pageSize) {
        Page<Bridge> page = PageHelper.startPage(pageNum,pageSize);
        List<Bridge> bridges =  bridgeDao.selectAllBridge();
        PageInfo<Bridge> pageInfo = new PageInfo<>(bridges,5);
        return pageInfo;
    }

    @Override
    public void save(Bridge bridge) {
        bridgeDao.insertByKey(bridge);
    }

    @Override
    public Bridge getSensorByID(String bridgeID) {
        return bridgeDao.getBridgeByID(bridgeID);
    }

    @Override
    public void update(String bridgeID, Bridge bridge) {
        //Bridge tempBridge = bridge;
        /*Bridge tempBridge = bridgeDao.getBridgeByID(bridgeID);
        tempBridge.setBridgeID(bridge.getBridgeID());
        tempBridge.setLowerThreshold(bridge.getLowerThreshold());
        tempBridge.setSensorID(bridge.getSensorID());
        tempBridge.setSensorLocation(bridge.getSensorLocation());
        tempBridge.setSensorName(bridge.getSensorName());
        tempBridge.setSensorStatus(bridge.getSensorStatus());
        tempBridge.setUpperThreshold(bridge.getUpperThreshold());
        bridgeDao.updateByKey(tempBridge);*/
        bridgeDao.updateByKey(bridge);
    }

    @Override
    public void delete(String bridgeID) {
        bridgeDao.deleteByKey(bridgeID);
    }

}