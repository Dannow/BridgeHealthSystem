package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Data
public class BridgeLocationResult {
    private String longitude;
    private String dimension;

    public BridgeLocationResult(Bridge bridge){
        this.longitude = bridge.getLongitude();
        this.dimension = bridge.getDimension();
    }
}
