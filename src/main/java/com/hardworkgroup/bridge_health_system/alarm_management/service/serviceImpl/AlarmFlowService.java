package com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "alarmFlowService")
public class AlarmFlowService implements JavaDelegate {
/*    private Expression flowName;
    private Expression keys;*/
    private Expression values;
/*    private Expression contents;
    private Expression equNames;
    private Expression equCodes;
    private Expression duration;

    private String keysString;
    private String valuesString;
    private String contentsString;
    private String equNamesString;
    private String equCodesString;

    private String codeName;
    private Integer codeValue;
    private String lineName;
    private Long timestamp;*/

/*    private boolean armFlag = false;*/
    @Override
    public void execute(DelegateExecution execution) {
        log.info("-------------------开始");
/*        String flowNameStr = (String)flowName.getValue(execution);
        String durationStr = (String)duration.getValue(execution);

        keysString = (String)keys.getValue(execution);
        valuesString = (String)values.getValue(execution);
        contentsString = (String)contents.getValue(execution);
        equNamesString = (String)equNames.getValue(execution);
        equCodesString = (String)equCodes.getValue(execution);

        codeName = (String)execution.getVariable("code");
        codeValue = (Integer)execution.getVariable("value");
        lineName = (String)execution.getVariable("line");
        timestamp = (Long)execution.getVariable("timestamp");*/

/*        armFlag = false;*/

        /*String startTime = LocalDateTime.ofEpochSecond(timestamp - Long.parseLong(durationStr),
                0, ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        String endTime = LocalDateTime.ofEpochSecond(timestamp + Long.parseLong(durationStr),
                0, ZoneOffset.ofHours(8)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));*/
        System.out.println("-------------------------");
        /*switch (flowNameStr) {
            case "CC_SLIP_SLIDE":
                CCSlipSlideTask(execution);
                break;
            case "TRAIN_SLIDE":
                //trainSlideTask(execution, startTime, endTime);
                break;
            case "SLIDE_SUPPRESSION":
            case "SLIDE_ACTIVE":
            case "WHEEL_DIAMETER_DIFF":
                //slideVehTask(execution, startTime, endTime);
                break;
            default:
                break;
        }*/
        log.info("-------------------结束");
    }
    // 分析：CC检测到打滑
    private void CCSlipSlideTask(DelegateExecution execution) {
        /*Integer value = Integer.parseInt(valuesString);

        if (keysString.equals(codeName) && value.equals(codeValue)) {
            AlarmData alarmData = new AlarmData();
            alarmData.setLineName(lineName);
            alarmData.setAlarmTime(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
            //alarmData.setArmUuid(UUID.randomUUID().toString());
            alarmData.setArmContent(contentsString);
            alarmData.setArmEquName(equNamesString);
            alarmData.setArmEquCode(equCodesString);

            execution.setVariable("alarmDataString", JSON.toJSONString(alarmData));
            execution.setVariable("isCCAlarm", "true");
        } else {
            execution.setVariable("isCCAlarm", "false");
        }*/
    }
}
