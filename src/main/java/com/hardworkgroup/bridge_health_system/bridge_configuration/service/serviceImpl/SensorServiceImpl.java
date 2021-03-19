package com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.dao.SensorDao;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TDimSensor)表服务实现类
 *
 * @author makejava
 * @since 2020-12-27 10:47:29
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorDao sensorDao;

    @Override
    public PageInfo<Sensor> findAll(int pageNum, int pageSize) {
        Page<Sensor> page = PageHelper.startPage(pageNum,pageSize);
        List<Sensor> sensors =  sensorDao.selectAllSensor();
        PageInfo<Sensor> pageInfo = new PageInfo<>(sensors,5);

        /*for (InspectionRecord inspectionRecord : pageInfo.getList()) {
            Set<Role> roles = roleDao.getRoleByUserId(user.getUserID());
            user.setRoles(roles);
        }*/
        return pageInfo;
    }

    @Override
    public void save(Sensor sensor) {
        sensorDao.insertByKey(sensor);
    }

    @Override
    public Sensor getSensorByID(String sensorID) {
        return sensorDao.getSensorByID(sensorID);
    }

    @Override
    public void update(Sensor sensor) {
        sensorDao.updateByKey(sensor);
    }

    @Override
    public void delete(String sensorID) {
        sensorDao.deleteByKey(sensorID);
    }

    // 获得传感器对应的数据
    public Sensor getSensorOriginalData(String sensorID){
        return sensorDao.getSensorOriginalData(sensorID);
    }
}