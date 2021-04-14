package com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.alarm_management.dao.AlarmInformationDao;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmInformationWithBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmDataServiceImpl {

    @Resource
    AlarmInformationDao alarmInformationDao;

    /**
     * 插入报警数据
     */
    public void save(AlarmInformation alarmInformation) {
        alarmInformationDao.insertByKey(alarmInformation);
    }

    /**
     * 查询所有报警数据
     */
    public PageInfo<AlarmInformationWithBridge> findAll(int pageNum, int pageSize) {
        Page<AlarmInformation> page = PageHelper.startPage(pageNum,pageSize);
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllAlarmInformation();
        List<AlarmInformationWithBridge> alarmInformationWithBridges = new ArrayList<>();
        for (AlarmInformation information : alarmInformation) {
            alarmInformationWithBridges.add(new AlarmInformationWithBridge(information));
        }
        return new PageInfo<>(alarmInformationWithBridges,5);
    }

    /**
     * 查询所有报警数据
     */
    public List<AlarmInformationWithBridge> findAll() {
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllAlarmInformation();
        List<AlarmInformationWithBridge> alarmInformationWithBridges = new ArrayList<>();
        for (AlarmInformation information : alarmInformation) {
            alarmInformationWithBridges.add(new AlarmInformationWithBridge(information));
        }
        return alarmInformationWithBridges;
    }

    /**
     * 根据桥梁ID查询所有报警数据
     */
    public PageInfo<AlarmInformationWithBridge> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<AlarmInformation> page = PageHelper.startPage(pageNum,pageSize);
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllByBridgeID(bridgeID);
        List<AlarmInformationWithBridge> alarmInformationWithBridges = new ArrayList<>();
        for (AlarmInformation information : alarmInformation) {
            alarmInformationWithBridges.add(new AlarmInformationWithBridge(information));
        }
        return new PageInfo<>(alarmInformationWithBridges,5);
    }

    /**
     * 根据Id查询报警信息
     */
    public AlarmInformation getAlarmInformationByID(String alarmInformationID) {
        return alarmInformationDao.getAlarmInformationByID(alarmInformationID);
    }

    public void update(String id, AlarmInformation alarmInformation) {
        alarmInformationDao.updateByKey(alarmInformation);
    }

    public void delete(String alarmInformationID) {
        alarmInformationDao.deleteByKey(alarmInformationID);
    }

    public PageInfo<AlarmInformationWithBridge> findAllByAlarmConfirmStatus(Integer alarmConfirmStatus, int pageNum, int pageSize) {
        Page<AlarmInformation> page = PageHelper.startPage(pageNum,pageSize);
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllByAlarmConfirmStatus(alarmConfirmStatus);
        List<AlarmInformationWithBridge> alarmInformationWithBridges = new ArrayList<>();
        for (AlarmInformation information : alarmInformation) {
            alarmInformationWithBridges.add(new AlarmInformationWithBridge(information));
        }
        return new PageInfo<>(alarmInformationWithBridges,5);
    }

    // 获取管理员未处理的报警信息
    public List<AlarmInformation> getUnprocessedAlarmInformation(Integer bridgeID){
        return alarmInformationDao.getUnprocessedAlarmInformation(bridgeID);
    }
}
