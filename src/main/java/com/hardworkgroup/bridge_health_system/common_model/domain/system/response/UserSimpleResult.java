package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSimpleResult {
    private String id;
    private String username;
    public UserSimpleResult(String id,String username){
        this.id = id;
        this.username = username;
    }
}
