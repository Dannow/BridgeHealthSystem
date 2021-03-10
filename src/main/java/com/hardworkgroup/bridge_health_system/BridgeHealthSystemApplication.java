package com.hardworkgroup.bridge_health_system;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

//1.配置springboot的包扫描
@SpringBootApplication
@MapperScan({"com.hardworkgroup.bridge_health_system.permission_management.dao", "com.hardworkgroup.bridge_health_system.bridge_inspection.dao"})
public class BridgeHealthSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BridgeHealthSystemApplication.class, args);
    }
}
