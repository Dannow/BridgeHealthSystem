package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Data
public class BridgeSimpleResult {
    private String bridgeName;
    private int bridgeLength;
    private String bridgeAddress;
    private String constructionCompany;
    private String bridgePicture;
    private String bridgeID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date constructionDate;
    public BridgeSimpleResult(Bridge bridge){
        this.bridgeID = bridge.getBridgeID();
        this.bridgeName = bridge.getBridgeName();
        this.bridgeLength = bridge.getBridgeLength();
        this.bridgeAddress = bridge.getBridgeAddress();
        this.constructionCompany = bridge.getConstructionCompany();
        this.constructionDate = bridge.getConstructionDate();
        this.bridgePicture = bridge.getBridgePicture();
    }
}
