package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor
public class SimplePlan {
    private Integer inspectionPlanID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionEndTime;

    private Integer inspectionCompletionStatus;

    private Integer inspectionCheckInStatus;

    private String inspectionPlanTitle;

    private Integer bridgeID;

    private String bridgeName;

    private Integer userID;

    private String userName;

    public SimplePlan(InspectionPlan inspectionPlan){
        this.inspectionPlanID = inspectionPlan.getInspectionPlanID();
        this.inspectionStartTime = inspectionPlan.getInspectionStartTime();
        this.inspectionEndTime = inspectionPlan.getInspectionEndTime();
        this.inspectionCompletionStatus = inspectionPlan.getInspectionCompletionStatus();
        this.inspectionCheckInStatus = inspectionPlan.getInspectionCheckInStatus();
        this.inspectionPlanTitle = inspectionPlan.getInspectionPlanTitle();
        this.bridgeID = inspectionPlan.getBridgeID();
        this.bridgeName = inspectionPlan.getBridge().getBridgeName();
        this.userID = inspectionPlan.getUserID();
        this.userName = inspectionPlan.getUser().getUserName();
    }
}
