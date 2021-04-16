package com.hardworkgroup.bridge_health_system.common_model.domain.system.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserWithDateResult {
    private String sessionId;

    private Long current;
    public UserWithDateResult(String sessionId){
        this.sessionId = sessionId;
        this.current = System.currentTimeMillis()+1800000;
    }
}
