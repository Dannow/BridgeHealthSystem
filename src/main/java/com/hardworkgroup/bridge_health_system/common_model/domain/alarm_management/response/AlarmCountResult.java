package com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response;

import lombok.Data;

@Data
public class AlarmCountResult {
    // 传感器名
    private String sensorName;
    // 报警次数
    private int alarmCount;

    public AlarmCountResult(String sensorName, int alarmCount) {
        this.sensorName = sensorName;
        this.alarmCount = alarmCount;
    }
}
