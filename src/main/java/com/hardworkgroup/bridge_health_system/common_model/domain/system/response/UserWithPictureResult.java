package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWithPictureResult {
    private String sessionId;
    private String userPicture;
    public UserWithPictureResult(String sessionId, String userPicture){
        this.sessionId = sessionId;
        this.userPicture = userPicture;
    }
}
