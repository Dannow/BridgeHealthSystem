package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.ActFlowCommServiceImpl;
import com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl.AlarmDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.websocketNetty.NettyConfig;
import com.hardworkgroup.bridge_health_system.realTime_dataCollection.service.PushService;
import com.hardworkgroup.bridge_health_system.system_common.utils.JsonUtil;
import com.hardworkgroup.bridge_health_system.system_common.utils.SendMessageUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PushServiceImpl implements PushService {
    @Autowired
    private AlarmDataServiceImpl alarmDataService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private SensorServiceImpl sensorServiceImpl;

    @Resource
    private ActFlowCommServiceImpl actFlowCommService;
    /**
     * 推送给指定用户
     */
    @Override
    public void pushMsgToOne(User user, Object msg) {
        // 获得存放用户与channel的对应信息的Map
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        // 根据userID获得对应的channel
        Channel channel = userChannelMap.get(user.getUserID().toString());
        // 获取数据
        AlarmInformation alarmInformation =(AlarmInformation)msg;

        // 发短信提醒
        SendMessageUtils.sendMsg(user.getUserPhone(),user.getBridge().getBridgeName(),user.getUserName(),"alarm");
        //发送邮件
        sendEmail();
        // 如果有对应的channel（既可以用户在线），则直接发送给他
        if (channel != null){
            String jsonStr = JSON.toJSONString(alarmInformation);
            // 将信息以json格式发到userID对应的channel中
            channel.writeAndFlush(new TextWebSocketFrame(jsonStr));
            //报警信息保存到数据库
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

    public void sendEmail() {
        Map<String,Object> variables = new HashMap<>();
        Sensor sensor = sensorServiceImpl.getSensorByID("10");
        String workFlowName = "alarm";
        variables.put("assignee0",1);
        variables.put("Sensor",sensor);
        variables.put("alarmData",100);
        log.info("【启动流程】，workFlowName ：{}",workFlowName);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName,variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        List<Map<String, Object>> taskList = actFlowCommService.myTaskList(1);
        if(!CollectionUtils.isEmpty(taskList)){
            for (Map<String, Object> map : taskList) {
                if(map.get("assignee").toString().equals("1") &&
                        map.get("processDefinitionId").toString().equals(processDefinitionId)){
                    log.info("processDefinitionId is {}",map.get("processDefinitionId").toString());
                    log.info("taskid is {}",map.get("taskid").toString());
                    actFlowCommService.completeProcess("确认",map.get("taskid").toString(),1);
                }
            }
        }
    }
}
