package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl.ActFlowCommServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.PlanFlowInfo;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionPlanFlowController)表控制层
 *
 * @author hui
 * @since 2021-05-24 18:11:59
 */
//解决跨域
@CrossOrigin
@Slf4j
@RestController
@Api(tags = "巡检计划流程管理接口")
@RequestMapping("/bridgeInspection/inspection/flow")
public class InspectionPlanFlowController extends BaseController {

    @Resource
    private ActFlowCommServiceImpl actFlowCommService;

    /**
     * 系统管理员确认巡检计划信息并通知桥梁巡检员
     */
    @Transactional
    @RequestMapping(value = "/auditPlan",method = RequestMethod.POST)
    public Result auditPlan(@RequestBody InspectionPlan inspectionPlan){
        Task task = actFlowCommService.singleTask("inspectionPlan1", inspectionPlan.getInspectionPlanID());
        log.info(task.getId());
        actFlowCommService.completeProcess("确认",task.getId(),this.userId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 桥梁巡检员确认巡检计划安排
     */
    @Transactional
    @RequestMapping(value = "/completePlan",method = RequestMethod.POST)
    public Result completePlan(@RequestBody InspectionPlan inspectionPlan){
        Task task = actFlowCommService.singleTask("inspectionPlan1", inspectionPlan.getInspectionPlanID());
        log.info(task.getId());
        actFlowCommService.completeProcess("确认",task.getId(),this.userId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询巡检计划历史流程
     */
    @Transactional
    @RequestMapping(value = "/historyPlan",method = RequestMethod.POST)
    public Result historyPlan(@RequestBody InspectionPlan inspectionPlan){
        String bussiness = "inspectionPlan1:" + inspectionPlan.getInspectionPlanID();
        List<PlanFlowInfo> planFlowInfos = actFlowCommService.searchHistory(bussiness);
        return new Result(ResultCode.SUCCESS,planFlowInfos);
    }
}
