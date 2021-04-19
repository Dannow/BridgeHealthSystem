package com.hardworkgroup.bridge_health_system;


import com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.socketNetty.NettyServer;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.websocketNetty.WebSocketNettyServer;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
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
public class BridgeHealthSystemApplication implements CommandLineRunner{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BridgeHealthSystemApplication.class, args);

//        // 启动SocketNetty服务端
//        NettyServer nettyServer = new NettyServer();
//
//        //启动WebsocketNetty服务端
//        WebSocketNettyServer webSocketNettyServer = new WebSocketNettyServer();
//
//        // 开启线程
//        Thread nettyServerThread = new Thread(nettyServer);
//        Thread  webSocketNettyServerThread = new Thread(webSocketNettyServer);
//        nettyServerThread.start();
//        webSocketNettyServerThread.start();

    }

    @Async
    public void run(String... args) throws Exception {
        /**
         * 使用异步注解方式启动netty服务端服务
         */

        // 启动SocketNetty服务端
        WebSocketNettyServer webSocketNettyServer = new WebSocketNettyServer();
        NettyServer nettyServer = new NettyServer();

        //启动WebsocketNetty服务端


        // 创建线程
        Thread nettyServerThread = new Thread(nettyServer);
        Thread  webSocketNettyServerThread = new Thread(webSocketNettyServer);
        // 开启线程
        nettyServerThread.start();
        webSocketNettyServerThread.start();

    }

    @Value("${server.http.port}")
    private int httpPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
        return tomcat;
    }

    // 配置http
    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        return connector;
    }
}
