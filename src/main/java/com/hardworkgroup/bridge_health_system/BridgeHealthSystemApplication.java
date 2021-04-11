package com.hardworkgroup.bridge_health_system;


import com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetSocketAddress;

//1.配置springboot的包扫描
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class})
@MapperScan({"com.hardworkgroup.bridge_health_system.permission_management.dao",
        "com.hardworkgroup.bridge_health_system.bridge_inspection.dao",
        "com.hardworkgroup.bridge_health_system.alarm_management.dao",
        "com.hardworkgroup.bridge_health_system.bridge_configuration.dao",
        "com.hardworkgroup.bridge_health_system.activiti.dao",
        "com.hardworkgroup.bridge_health_system.realTime_dataCollection.dao",
        "com.hardworkgroup.bridge_health_system.data_analysis.dao"})
@EnableScheduling
@EnableAsync
public class BridgeHealthSystemApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BridgeHealthSystemApplication.class, args);
        // 启动服务端
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress(8087));
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(BridgeHealthSystemApplication.class);
//    }
//    @Async
//    public void run(String... args) throws Exception {
//        /**
//         * 使用异步注解方式启动netty服务端服务
//         */
//        /**启动服务端*/
//        NettyServer nettyServer = new NettyServer();
//        nettyServer.start(new InetSocketAddress(8087));
//    }
}
