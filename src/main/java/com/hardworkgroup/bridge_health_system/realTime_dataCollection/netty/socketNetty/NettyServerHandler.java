package com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.socketNetty;

import com.alibaba.fastjson.JSONObject;
import com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl.AlarmDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.SensorService;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataFire;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataSmoke;
import com.hardworkgroup.bridge_health_system.common_model.domain.realTime_dataCollection.entity.RawDataTemperature;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.PushService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataFireService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataSmokeService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.RawDataTemperatureService;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl.PushServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
*服务端业务处理
* */
@Component
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private RawDataTemperatureService rawDataTemperatureService;
    @Autowired
    private RawDataSmokeService rawDataSmokeService;
    @Autowired
    private RawDataFireService rawDataFireService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private PushService pushService;
    @Autowired
    private AlarmDataServiceImpl alarmDataService;

    private static NettyServerHandler nettyServerHandler;

    /**配合@Component注解获取service层的bean*/
    @PostConstruct
    public void init(){
        nettyServerHandler = this;
        nettyServerHandler.rawDataTemperatureService = this.rawDataTemperatureService;
        nettyServerHandler.rawDataSmokeService = this.rawDataSmokeService;
        nettyServerHandler.rawDataSmokeService = this.rawDataSmokeService;
        nettyServerHandler.rawDataFireService = this.rawDataFireService;
        nettyServerHandler.sensorService = this.sensorService;
        nettyServerHandler.pushService = this.pushService;
    }

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取客户端的IP
        InetSocketAddress insocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String ip = insocket.getAddress().getHostAddress();
        log.info("客户端:"+ip+"发起连接！！！！");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端的数据
        String tem = msg.toString();
        // String 转换成 Json
        JSONObject sensorSocketData = JSONObject.parseObject(tem);
        // 获取时间
        String time = (String) sensorSocketData.get("sensorDataTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sensorDataTime = sdf.parse(time);
        // 获取温度数据
        String temperatureDataTmp = sensorSocketData.get("temperature").toString();
        float temperatureData = Float.parseFloat(temperatureDataTmp);
        // 获取烟雾传感器数据
        int smokeData = (int) sensorSocketData.get("smoke");
        // 获取火焰传感器数据
        int fireData = (int) sensorSocketData.get("fire");

        // 温度数据加到数据库
        Sensor temperatureSensor = nettyServerHandler.sensorService.getSensorByID("1");
        // 判断报警
        isAlarm(temperatureSensor, "9", (int)temperatureData);
        nettyServerHandler.rawDataTemperatureService.insertTemperatureData(new RawDataTemperature(sensorDataTime, temperatureData, temperatureSensor));

        // 烟雾传感器数据加到数据库
        Sensor smokeSensor = nettyServerHandler.sensorService.getSensorByID("18");
        // 判断报警
        isAlarm(temperatureSensor, "9", smokeData);
        nettyServerHandler.rawDataSmokeService.insertRawDataSmoke(new RawDataSmoke(sensorDataTime, smokeData, smokeSensor));

        // 火焰传感器数据加到数据库
        Sensor fireSensor = nettyServerHandler.sensorService.getSensorByID("19");
        // 判断报警
        isAlarm(temperatureSensor, "9", fireData);
        nettyServerHandler.rawDataFireService.insertRawDataFire(new RawDataFire(sensorDataTime, fireData, fireSensor));

        log.info("sensorDataTime: "+sensorDataTime+" temperatureData: "+temperatureData+" smokeData: "+smokeData+" fireData: "+fireData);

        // 返回消息给客户端
//        ctx.writeAndFlush("receive OK!");
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常
        cause.printStackTrace();
        // 关闭通道
        ctx.close();
    }

    /**
     * 客户端与服务端 断连时 执行
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException
    {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        log.info("断开的客户端IP:"+clientIp);
    }

    /**
     * 判断是否报警
     */
    public void isAlarm(Sensor sensor, String userID, int realTimeData){
        
        // 保存报警信息
        AlarmInformation alarmInformation = new AlarmInformation();
        alarmInformation.setSensorID(sensor.getSensorID());
        alarmInformation.setAlarmDealStatus(0);
        alarmInformation.setAlarmConfirmStatus(0);
        Date alarmTime = new Date();
        alarmInformation.setAlarmTime(alarmTime);

        // 判断是否超过上限
        if (realTimeData >= sensor.getUpperThreshold()){
            alarmInformation.setAlarmType("数据超过阀值");
            String alarmDetail = sensor.getSensorName() + "当前数值为: " + realTimeData + " 超过阀值";
            alarmInformation.setAlarmDetail(alarmDetail);

            // 消息推送
            nettyServerHandler.pushService.pushMsgToOne(userID,alarmInformation);
        // 判断是否低于阀值
        }else if (realTimeData < sensor.getLowerThreshold()){
            alarmInformation.setAlarmType("数据低于阀值");
            String alarmDetail = sensor.getSensorName() + "当前数值为: " + realTimeData + " 低于阀值";
            alarmInformation.setAlarmDetail(alarmDetail);

            // 消息推送
            nettyServerHandler.pushService.pushMsgToOne(userID,alarmInformation);
        }


    }
}
