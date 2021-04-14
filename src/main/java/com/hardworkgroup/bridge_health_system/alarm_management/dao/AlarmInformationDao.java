package com.hardworkgroup.bridge_health_system.alarm_management.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmInformationWithBridge;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AlarmInformationDao extends Mapper<AlarmInformation> {

    void insertByKey(AlarmInformation alarmInformation);

    List<AlarmInformation> selectAllAlarmInformation();

    AlarmInformation getAlarmInformationByID(String alarmInformationID);

    void updateByKey(AlarmInformation alarmInformation);

    void deleteByKey(String alarmInformationID);

    List<AlarmInformation> selectAllByAlarmConfirmStatus(Integer alarmConfirmStatus);

    List<AlarmInformation> selectAllByBridgeID(Integer bridgeID);

    // 获取管理员未处理的报警信息
    public List<AlarmInformation> getUnprocessedAlarmInformation(Integer bridgeID);
}
