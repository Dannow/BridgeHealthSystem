package com.hardworkgroup.bridge_health_system;

import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BridgeHealthSystemApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.getUserByID("1");
    }

}
