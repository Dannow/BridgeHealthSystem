package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.SensorDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TDimSensor)表服务实现类
 *
 * @author makejava
 * @since 2020-12-27 10:47:29
 */
@Service("tDimSensorService")
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorDao sensorDao;

    @Override
    public List<Sensor> selectAllTDimSensor() {
        //List<TDimSensor> tDimSensors =  tDimSensordao.getallTDimSensor();
        //System.out.println(tDimSensors);
        return sensorDao.selectAll();
    }

    @Override
    public Sensor selectByPrimaryKey(Integer sensors) {

        return sensorDao.selectByPrimaryKey(sensors);
    }

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
    public void update(String sensorID, Sensor sensor) {
        Sensor tempSensor = sensorDao.getSensorByID(sensorID);
        tempSensor.setBridgeID(sensor.getBridgeID());
        tempSensor.setLowerThreshold(sensor.getLowerThreshold());
        tempSensor.setSensorId(sensor.getSensorId());
        tempSensor.setSensorLocation(sensor.getSensorLocation());
        tempSensor.setSensorName(sensor.getSensorName());
        tempSensor.setSensorStatus(sensor.getSensorStatus());
        tempSensor.setUpperThreshold(sensor.getUpperThreshold());
        sensorDao.updateByKey(tempSensor);
    }

    @Override
    public void delete(String sensorID) {
        sensorDao.deleteByKey(sensorID);
    }

}