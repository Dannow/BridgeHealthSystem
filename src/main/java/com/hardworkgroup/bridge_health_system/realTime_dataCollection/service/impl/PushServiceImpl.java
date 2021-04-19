package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl.AlarmDataServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.websocketNetty.NettyConfig;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.PushService;
import com.hardworkgroup.bridge_health_system.system_common.utils.JsonUtil;
import com.hardworkgroup.bridge_health_system.system_common.utils.SendMessageUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PushServiceImpl implements PushService {
    @Autowired
    private AlarmDataServiceImpl alarmDataService;

    /**
     * 推送给指定用户
     */
    @Override
    public void pushMsgToOne(User user, Object msg) {
        // 获得存放用户与channel的对应信息的Map
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        // 根据userID获得对应的channel
        Channel channel = userChannelMap.get(user.getUserID());
        // 获取数据
        AlarmInformation alarmInformation =(AlarmInformation)msg;

        // 发短信提醒
        SendMessageUtils.sendMsg(user.getUserPhone(),user.getBridge().getBridgeName(),user.getUserName());

        // 如果有对应的channel（既可以用户在线），则直接发送给他
        if (channel != null){
            JSONObject jsonData = (JSONObject) JSON.toJSON(msg);
            // 将信息以json格式发到userID对应的channel中
            channel.writeAndFlush(jsonData);
            alarmDataService.save(alarmInformation);
        // 若用户不在线，则保留在数据库中
        }else {
            //报警信息保存到数据库
            alarmDataService.save(alarmInformation);
        }
    }

    /**
     * 推送给所有用户
     */
    @Override
    public void pushMsgToAll(String msg) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
