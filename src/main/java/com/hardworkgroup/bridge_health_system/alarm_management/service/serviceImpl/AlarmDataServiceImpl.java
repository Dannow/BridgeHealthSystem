package com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.alarm_management.dao.AlarmInformationDao;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public PageInfo<AlarmInformation> findAll(int pageNum, int pageSize) {
        Page<AlarmInformation> page = PageHelper.startPage(pageNum,pageSize);
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllAlarmInformation();
        PageInfo<AlarmInformation> pageInfo = new PageInfo<>(alarmInformation,5);
        return pageInfo;
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

    public PageInfo<AlarmInformation> findAllByAlarmConfirmStatus(Integer alarmConfirmStatus, int pageNum, int pageSize) {
        Page<AlarmInformation> page = PageHelper.startPage(pageNum,pageSize);
        List<AlarmInformation> alarmInformation =  alarmInformationDao.selectAllByAlarmConfirmStatus(alarmConfirmStatus);
        return new PageInfo<>(alarmInformation,5);
    }
}
