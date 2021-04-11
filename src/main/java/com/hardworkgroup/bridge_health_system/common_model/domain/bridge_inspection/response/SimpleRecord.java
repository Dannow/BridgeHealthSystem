package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SimpleRecord {
    private Integer inspectionRecordID;

    private Integer inspectionPlanID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionTime;

    private String inspectionRecordTitle;

    private String inspectionContentDescription;

    private Integer bridgeID;

    private String bridgeName;

    private Integer userID;

    private String userName;

    //private Set<SimpleData> simpleData = new HashSet<>();

    public SimpleRecord(InspectionRecord inspectionRecord){
        this.inspectionRecordID = inspectionRecord.getInspectionRecordID();
        this.inspectionPlanID = inspectionRecord.getInspectionPlanID();
        this.inspectionTime = inspectionRecord.getInspectionTime();
        this.inspectionRecordTitle = inspectionRecord.getInspectionRecordTitle();
        this.inspectionContentDescription = inspectionRecord.getInspectionContentDescription();
        this.bridgeID = inspectionRecord.getInspectionPlan().getBridgeID();
        this.bridgeName = inspectionRecord.getInspectionPlan().getBridge().getBridgeName();
        this.userID = inspectionRecord.getInspectionPlan().getUserID();
        this.userName = inspectionRecord.getInspectionPlan().getUser().getUserName();
        /*for (InspectionData inspectionDatum : inspectionRecord.getInspectionData()) {
            this.simpleData.add(new SimpleData(inspectionDatum));
        }*/
    }
}
