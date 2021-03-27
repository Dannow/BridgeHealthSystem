package com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class AlarmServiceImpl implements JavaDelegate {

    private Expression alarmType;

    private String codeName;

    @Override
    public void execute(DelegateExecution execution) {
        String alarmTypeString = (String)alarmType.getValue(execution);
        codeName = (String)execution.getVariable("code");
        installAndSendAlarm(execution);
        /*switch (alarmTypeString) {
            case "CC_TRAIN_DIFF":
                installAndSendAlarm(execution, CombineAlarmEnum.SLIDE_SIG_TRAIN_DIFF);
                break;
            case "SLIDE_SUPPRESSION":
                installAndSendAlarm(execution, CombineAlarmEnum.SLIDE_SUPPRESSION);
                break;
            case "TRAIN_SLIDE":
                installAndSendAlarm(execution, CombineAlarmEnum.SLIDE_TRAIN);
                break;
            case "WHEEL_DIAMETER_DIFF":
                installAndSendAlarm(execution, CombineAlarmEnum.SLIDE_WHEEL_DIAMETER_DIFF);
                break;
            case "SLIDE_ACTIVE":
                installAndSendAlarm(execution, CombineAlarmEnum.SLIDE_ACTIVE);
                break;
            default:
                break;
        }*/
    }
    private void installAndSendAlarm(DelegateExecution execution/*, CombineAlarmEnum combineAlarmEnum*/) {
        /*CacheManagerImpl cacheManager = new CacheManagerImpl();
        String alarmDataStr = (String)execution.getVariable("alarmDataString");
        if (!alarmDataStr.isEmpty()) {
            AlarmData alarmData = JSON.parseObject(alarmDataStr, AlarmData.class);
            alarmData.setArmCode(combineAlarmEnum.getArmCode());
            alarmData.setArmDbm(combineAlarmEnum.getArmDbm());
            alarmData.setArmSource(combineAlarmEnum.getArmSource());
            alarmData.setArmEquType(combineAlarmEnum.getArmEquType());
            alarmData.setArmEquTypecode(combineAlarmEnum.getArmEquTypeCode());
            alarmData.setArmLevel(combineAlarmEnum.getArmLevel());
            kafkaProducer.sendAlarmMessage(JSON.toJSONString(alarmData));
            cacheManager.putCache(codeName, alarmData);
        }*/
    }
}
