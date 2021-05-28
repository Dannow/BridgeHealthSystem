package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;


@Data
@NoArgsConstructor
public class SimpleEvent {
    private Integer problemEventID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date problemCreationTime;

    private Integer maintenanceStatus;

    private Integer confirmStatus;

    private String problemTitle;

    private String problemDescription;

    private Integer inspectionRecordID;

    private Integer sensorID;

    private String sensorName;

    private Integer bridgeID;

    private String bridgeName;

    private Integer userID;

    private String userName;

    //private List<SimpleEventPicture> simpleEventPictures = new ArrayList<>();

    private List<String> simpleEventPictures = new ArrayList<>();

    public SimpleEvent(ProblemEvent problemEvent){
        this.problemEventID = problemEvent.getProblemEventID();
        this.problemCreationTime = problemEvent.getProblemCreationTime();
        this.maintenanceStatus = problemEvent.getInspectionRecordID();
        this.confirmStatus = problemEvent.getInspectionRecordID();
        this.problemTitle = problemEvent.getProblemTitle();
        this.problemDescription = problemEvent.getProblemDescription();
        this.inspectionRecordID = problemEvent.getInspectionRecordID();
        this.sensorID = problemEvent.getSensorID();
        this.sensorName = problemEvent.getSensor().getSensorName();
        this.bridgeID = problemEvent.getSensor().getBridgeID();
        this.bridgeName = problemEvent.getSensor().getBridge().getBridgeName();
        this.userID = problemEvent.getUserID();
        this.userName = problemEvent.getUser().getUserName();
        /*for (ProblemEventPicture problemEventPicture : problemEvent.getProblemEventPictures()) {
            this.simpleEventPictures.add(new SimpleEventPicture(problemEventPicture));
        }*/
        for (ProblemEventPicture problemEventPicture : problemEvent.getProblemEventPictures()) {
            this.simpleEventPictures.add("http://121.199.75.149:9999/img/"+problemEventPicture.getProblemPicture());
        }
    }
}
