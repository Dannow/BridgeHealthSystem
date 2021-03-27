package com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.dao.SensorHealthDao;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorHealthService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.SensorHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SensorHealthServiceImpl)表服务实现类
 *
 * @author makejava
 * @since 2020-12-27 10:47:29
 */
@Service
public class SensorHealthServiceImpl implements SensorHealthService {
    @Autowired
    private SensorHealthDao sensorHealthDao;

    @Override
    public PageInfo<SensorHealth> findAll(int pageNum, int pageSize) {
        Page<SensorHealth> page = PageHelper.startPage(pageNum, pageSize);
        List<SensorHealth> sensorHealths = sensorHealthDao.selectAllSensorHealth();
        return new PageInfo<>(sensorHealths, 5);
    }

    @Override
    public PageInfo<SensorHealth> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<SensorHealth> page = PageHelper.startPage(pageNum, pageSize);
        List<SensorHealth> sensorHealths = sensorHealthDao.selectAllByBridgeID(bridgeID);
        return new PageInfo<>(sensorHealths, 5);
    }

    @Override
    public void save(SensorHealth sensorHealth) {
        sensorHealthDao.insertByKey(sensorHealth);
    }

    @Override
    public SensorHealth getSensorByID(String sensorHealthID) {
        return sensorHealthDao.getSensorHealthByID(sensorHealthID);
    }

    @Override
    public void update(SensorHealth sensorHealth) {
        sensorHealthDao.updateByKey(sensorHealth);
    }

    @Override
    public void delete(String sensorHealthID) {
        sensorHealthDao.deleteByKey(sensorHealthID);
    }
}