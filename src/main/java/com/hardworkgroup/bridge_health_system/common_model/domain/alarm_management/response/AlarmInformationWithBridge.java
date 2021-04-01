package com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class AlarmInformationWithBridge {
    private Integer alarmInformationID;

    private Integer userID;

    private Integer sensorID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    private String alarmType;

    private Integer alarmConfirmStatus;

    private Integer alarmDealStatus;

    private String alarmDetail;

    private String sensorName;

    private Integer bridgeID;
    private String bridgeName;

    public AlarmInformationWithBridge(AlarmInformation alarmInformation){
        this.alarmInformationID = alarmInformation.getAlarmInformationID();
        this.alarmTime = alarmInformation.getAlarmTime();
        this.alarmType = alarmInformation.getAlarmType();
        this.alarmConfirmStatus = alarmInformation.getAlarmConfirmStatus();
        this.alarmDealStatus = alarmInformation.getAlarmDealStatus();
        this.alarmDetail = alarmInformation.getAlarmDetail();
        this.sensorID = alarmInformation.getSensorID();
        this.sensorName = alarmInformation.getSensor().getSensorName();
        this.bridgeID = alarmInformation.getSensor().getBridgeID();
        this.bridgeName = alarmInformation.getSensor().getBridge().getBridgeName();
    }
}
