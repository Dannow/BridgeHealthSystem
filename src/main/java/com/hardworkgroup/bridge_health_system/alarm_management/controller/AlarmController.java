package com.hardworkgroup.bridge_health_system.alarm_management.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.ActFlowCommServiceImpl;
import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.SiteMessageServiceImpl;
import com.hardworkgroup.bridge_health_system.alarm_management.service.serviceImpl.AlarmDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.BridgeService;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.SiteMessage;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.entity.AlarmInformation;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmCountResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmInformationWithBridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.alarm_management.response.AlarmCountResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response.BridgeSimpleResult;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.Role;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import com.hardworkgroup.bridge_health_system.permission_management.service.UserService;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/alarm_management")
public class AlarmController extends BaseController {
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private SensorServiceImpl sensorServiceImpl;

    @Resource
    private ActFlowCommServiceImpl actFlowCommService;

    @Resource
    private AlarmDataServiceImpl alarmDataService;

    @Resource
    private SiteMessageServiceImpl siteMessageService;

    @Autowired
    private BridgeService bridgeService;

    @Autowired
    private UserService userService;

    /**
     * 启动报警流程
     * @param workFlowName
     * @return
     */
    @Transactional
    @RequestMapping(value = "/startWorkflow/{workFlowName}",method = RequestMethod.GET)
    public Result startWorkflow(@PathVariable(value = "workFlowName")String workFlowName) {
        // 通过流程定义的key启动，选取最高的version启动
        Sensor sensor = sensorServiceImpl.getSensorByID("10");
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee0",this.userId);
        variables.put("Sensor",sensor);
        variables.put("alarmData",100);
        log.info("【启动流程】，workFlowName ：{}", workFlowName);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workFlowName,variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        List<Map<String, Object>> taskList = actFlowCommService.myTaskList(this.userId);
        if(!CollectionUtils.isEmpty(taskList)){
            for (Map<String, Object> map : taskList) {
                if(map.get("assignee").toString().equals(this.userId.toString()) &&
                        map.get("processDefinitionId").toString().equals(processDefinitionId)){
                    log.info("processDefinitionId is {}",map.get("processDefinitionId").toString());
                    log.info("taskid is {}",map.get("taskid").toString());
                    actFlowCommService.completeProcess("确认",map.get("taskid").toString(),this.userId);
                    AlarmInformation alarmInformation =new AlarmInformation();
                    alarmInformation.setSensorID(10);
                    alarmInformation.setAlarmType("数据超过阈值");
                    alarmInformation.setAlarmTime((Date) map.get("createTime"));
                    alarmInformation.setAlarmDealStatus(0);
                    alarmInformation.setAlarmConfirmStatus(0);
                    alarmInformation.setAlarmDetail(sensor.getSensorName()+"数据超过阈值");
                    alarmDataService.save(alarmInformation);
                }
            }
        }
        if (processInstance.getProcessInstanceId().length() > 0) {
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.SERVER_ERROR);
    }

    /**
     * 管理员确认报警信息并通知巡检员
     */
    @Transactional
    @RequestMapping(value = "/deliver",method = RequestMethod.POST)
    public Result deliverAlarmInformation(@RequestBody Map<String,Object> map) throws Exception {
        User user = BeanMapUtils.mapToBean(map, User.class);
        Sensor sensor = BeanMapUtils.mapToBean(map, Sensor.class);
        Bridge bridge = BeanMapUtils.mapToBean(map, Bridge.class);
        AlarmInformation alarmInformation = BeanMapUtils.mapToBean(map, AlarmInformation.class);
        String title = bridge.getBridgeName()+"的"+sensor.getSensorName()+"发生报警";
        String bussinessKey = "deliver"+":" + user.getUserID();
        //设置流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("User",user);
        variables.put("Title",title);
        variables.put("AlarmInformation",alarmInformation);
        log.info("【启动流程】，workFlowName ：deliver");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("deliver",bussinessKey,variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 巡检员确认报警信息
     */
    @Transactional
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    public Result completeAlarmInformation(){
        SiteMessage message = siteMessageService.findOne(this.userId);
        actFlowCommService.completeProcess("确认",message.getTaskID(),this.userId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取所有报警信息
     * @return 报警信息结果
     */
    @RequestMapping(value = "/alarmInformation" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<AlarmInformationWithBridge> pageInfo = alarmDataService.findAll(pageNum, pageSize);
        PageResult<AlarmInformationWithBridge> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 手机端获取所有报警信息
     * @return 报警信息结果
     */
    @RequestMapping(value = "/alarmInformation" , method = RequestMethod.GET)
    public Result findAll(){
        List<AlarmInformationWithBridge> all = alarmDataService.findAll();
        Map<String,Object> map =new HashMap<>();
        map.put("rows",all);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 根据管理员确认状态查询所有报警信息
     * @return 报警信息结果
     */
    @RequestMapping(value = "/alarmInformation/alarmConfirmStatus/{alarmConfirmStatus}" , method = RequestMethod.POST)
    public Result findAllByConfirmStatus(@PathVariable(value = "alarmConfirmStatus") Integer alarmConfirmStatus,@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String)map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<AlarmInformationWithBridge> pageInfo = alarmDataService.findAllByAlarmConfirmStatus(alarmConfirmStatus,pageNum, pageSize);
        PageResult<AlarmInformationWithBridge> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 查询管理员未处理报警信息报警信息
     * @return 报警信息结果
     */
    @RequestMapping(value = "/unprocessedAlarmInformation/{userID}" , method = RequestMethod.GET)
    public Result findUnprocessedAlarmInformation(@PathVariable(value = "userID") String userID){
        // 获取用户对应的桥梁
        User user = userService.getUserByID(userID);
        Integer bridgeID = user.getBridgeID();

        //判断用户角色是否为管理员
        Boolean isAdmin = false;
        Set<Role> roles = user.getRoles();
        for (Role role : roles){
            if (role.getRoleID().equals("33")){
                isAdmin = true;
            }
        }

        // 如果是管理员，返回消息
        if (isAdmin){
            List<AlarmInformation> unprocessedAlarmInformation = alarmDataService.getUnprocessedAlarmInformation(bridgeID);
            return new Result(ResultCode.SUCCESS,unprocessedAlarmInformation);
        }else {
            return new Result(ResultCode.SUCCESS,null);
        }
    }

    /**
     * 根据桥梁ID查询所有报警信息
     */
    @RequestMapping(value = "/alarmInformation/bridgeID/{bridgeID}" , method = RequestMethod.POST)
    public Result findAllByBridgeID(@PathVariable(value = "bridgeID") Integer bridgeID,@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String)map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<AlarmInformationWithBridge> pageInfo = alarmDataService.findAllByBridgeID(bridgeID,pageNum, pageSize);
        PageResult<AlarmInformationWithBridge> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }
    /**
     * 保存报警信息
     */
    @RequestMapping(value = "/alarmInformation/import",method = RequestMethod.POST)
    public Result save(@RequestBody AlarmInformation alarmInformation) {
        alarmDataService.save(alarmInformation);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询报警信息
     */
    @RequestMapping(value = "/alarmInformation/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        AlarmInformation alarmInformation = alarmDataService.getAlarmInformationByID(id);
        return new Result(ResultCode.SUCCESS , alarmInformation);
    }

    /**
     * 根据Id修改报警信息
     */
    @RequestMapping(value = "/alarmInformation/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody AlarmInformation alarmInformation){
        //调用Service更新
        alarmDataService.update(id , alarmInformation);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除报警信息
     */
    @RequestMapping(value = "/alarmInformation/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        alarmDataService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据桥梁ID获得告警次数
     */
    @RequestMapping(value = "/alarmNumberByBridgeID/{bridgeID}",method = RequestMethod.POST)
    public Result findAlarmNumberByBridgeID(@PathVariable(value = "bridgeID") String bridgeID, @RequestBody Map<String,String > map){
        // 获取天数
        int day = Integer.parseInt(map.get("day"));

        // 统计报警次数
        int count = 0;
        // 获得桥梁下所有传感器
        Bridge bridge = bridgeService.getSensorByBridgeID(bridgeID);
        Set<Sensor> sensors = bridge.getSensors();

        // 获得当前时间
        Date nowTime = new Date();

        // 得到指定的日期
        Calendar c = Calendar.getInstance();
        c.setTime(nowTime);
        c.add(Calendar.DAY_OF_MONTH, -day);
        Date targetTime = c.getTime();

        List<AlarmCountResult> alarmCountList = new ArrayList<>();

        // 遍历桥梁下传感器
        for (Sensor sensor : sensors){
            Sensor sensor2 = sensorServiceImpl.getAlarmInformationBySensorID(sensor.getSensorID());
            Set<AlarmInformation> alarmInformations = sensor2.getAlarmInformations();
            // 遍历传感器下报警信息
            for (AlarmInformation alarmInformation : alarmInformations){
                if (targetTime.before(alarmInformation.getAlarmTime()) || targetTime.equals(alarmInformation.getAlarmTime())){
                    count++;
                }
            }
            alarmCountList.add(new AlarmCountResult(sensor.getSensorName(),count));
            count = 0;
        }

        return new Result(ResultCode.SUCCESS, alarmCountList);
    }
}
