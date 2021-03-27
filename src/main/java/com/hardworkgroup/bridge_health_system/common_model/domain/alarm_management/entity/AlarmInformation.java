package com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "alarm_information")
@Data
@NoArgsConstructor
public class AlarmInformation implements Serializable {

    @Id
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

    @ManyToOne
    private Sensor sensor;
}
