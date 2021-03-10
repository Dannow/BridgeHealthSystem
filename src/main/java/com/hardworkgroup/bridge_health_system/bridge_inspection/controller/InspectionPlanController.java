package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionPlanServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (TPatrolPlan)表控制层
 *
 * @author makejava
 * @since 2021-01-16 18:11:59
 */
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class InspectionPlanController {
    /**
     * 服务对象
     */
    @Autowired
    private InspectionPlanServiceImpl inspectionPlanService;

    /**
     * 获取所有巡检计划列表
     * @return 巡检记录结果
     */
    @RequestMapping(value = "/plans" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<InspectionPlan> pageInfo = inspectionPlanService.findAll(pageNum, pageSize);
        PageResult<InspectionPlan> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 保存巡检记录
     */
    @RequestMapping(value = "/plan/import",method = RequestMethod.POST)
    public Result save(@RequestBody InspectionPlan inspectionPlan) {
        inspectionPlanService.save(inspectionPlan);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询巡检记录
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        InspectionPlan inspectionRecord = inspectionPlanService.getPlanByID(id);
        return new Result(ResultCode.SUCCESS , inspectionRecord);
    }

    /**
     * 根据Id修改巡检记录
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody InspectionPlan inspectionPlan){
        //调用Service更新
        inspectionPlanService.update(id , inspectionPlan);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检记录
     */
    @RequestMapping(value = "/plan/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        inspectionPlanService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}