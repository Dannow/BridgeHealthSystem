package com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.dao.BridgeDao;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.BridgeService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response.BridgeSimpleResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<BridgeSimpleResult> findAll() {
        List<Bridge> bridges = bridgeDao.selectAllBridge();
        List<BridgeSimpleResult> bridgeSimpleResults = new ArrayList<>();
        for (Bridge bridge : bridges) {
            bridge.setBridgePicture("http://121.199.75.149:9999/img/"+bridge.getBridgePicture());
            bridgeSimpleResults.add(new BridgeSimpleResult(bridge));
        }
        return bridgeSimpleResults;
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
        bridgeDao.updateByKey(bridge);
    }

    @Override
    public void delete(String bridgeID) {
        bridgeDao.deleteByKey(bridgeID);
    }

    // 获得桥梁下的所有传感器
    public Bridge getSensorByBridgeID(String bridgeID){
        return bridgeDao.getSensorByBridgeID(bridgeID);
    }

}