package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.ActFlowCommServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionPlanServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimplePlan;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (InspectionPlanController)表控制层
 *
 * @author makejava
 * @since 2021-01-16 18:11:59
 */
//解决跨域
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class InspectionPlanController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionPlanServiceImpl inspectionPlanService;

    @Resource
    private ActFlowCommServiceImpl actFlowCommService;

    /**
     * 获取所有巡检计划列表
     * @return 巡检计划结果
     */
    @RequestMapping(value = "/plans" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<SimplePlan> pageInfo = inspectionPlanService.findAll(pageNum, pageSize);
        PageResult<SimplePlan> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据桥梁Id查询巡检计划
     */
    @RequestMapping(value = "/plan/bridge/{bridgeID}" , method = RequestMethod.POST)
    public Result findByBridgeId(@PathVariable(value = "bridgeID") Integer bridgeID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        //根据bridgeID查询巡检计划
        PageInfo<SimplePlan> pageInfo = inspectionPlanService.getPlanByBridgeID(bridgeID, pageNum, pageSize);
        PageResult<SimplePlan> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 根据计划Id查询巡检计划
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        InspectionPlan inspectionRecord = inspectionPlanService.getPlanByID(id);
        SimplePlan simplePlan = new SimplePlan(inspectionRecord);
        return new Result(ResultCode.SUCCESS , simplePlan);
    }

    /**
     * 保存巡检计划
     */
    @RequestMapping(value = "/plan/import",method = RequestMethod.POST)
    @Transactional
    public Result save(@RequestBody InspectionPlan inspectionPlan) {
        Integer code = inspectionPlanService.save(inspectionPlan);
        if(code ==1){
            Integer inspectionPlanID = inspectionPlan.getInspectionPlanID();
            String formKey = "inspectionPlan";
            String beanName = formKey + "ServiceImpl";
            //使用流程变量设置字符串（格式 ： InspectionPlan:Id 的形式）
            //使用正在执行对象表中的一个字段BUSINESS_KEY(Activiti提供的一个字段)，让启动的流程（流程实例）关联业务
            String bussinessKey = formKey+":" + inspectionPlanID;
            ProcessInstance processInstance = actFlowCommService.startProcess(formKey, beanName, bussinessKey, inspectionPlanID,this.userId);
            //		流程实例ID
            String processDefinitionId = processInstance.getProcessDefinitionId();
            log.info("processDefinitionId is {}",processDefinitionId);
            List<Map<String, Object>> taskList = actFlowCommService.myTaskList(this.userId);
            if(!CollectionUtils.isEmpty(taskList)){
                for (Map<String, Object> map : taskList) {
                    if(map.get("assignee").toString().equals(this.userId.toString()) &&
                            map.get("processDefinitionId").toString().equals(processDefinitionId)){
                        log.info("processDefinitionId is {}",map.get("processDefinitionId").toString());
                        log.info("taskid is {}",map.get("taskid").toString());
                        actFlowCommService.completeProcess("确认",map.get("taskid").toString(),this.userId);
                    }

                }
            }
        }
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id修改巡检计划
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody InspectionPlan inspectionPlan){
        //调用Service更新
        inspectionPlanService.update(id , inspectionPlan);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检计划
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        inspectionPlanService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}