package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleEventPicture {

    private String problemPicture;

    public SimpleEventPicture(ProblemEventPicture problemEventPicture){
        this.problemPicture = "http://121.199.75.149:9999/img/"+ problemEventPicture.getProblemPicture();
    }
}
