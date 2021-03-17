package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.ProblemEventServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (ProblemEventController)表控制层
 *
 * @author makejava
 * @since 2021-01-16 18:11:59
 */
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class ProblemEventController {
    /**
     * 服务对象
     */
    @Autowired
    private ProblemEventServiceImpl problemEventService;

    /**
     * 获取所有问题事件列表
     * @return 巡检计划结果
     */
    @RequestMapping(value = "/problemEvents" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<ProblemEvent> pageInfo = problemEventService.findAll(pageNum, pageSize);
        PageResult<ProblemEvent> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据记录Id查询问题事件
     */
    @RequestMapping(value = "/problemEvent/record/{recordID}" , method = RequestMethod.POST)
    public Result findByBridgeId(@PathVariable(value = "recordID") Integer recordID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        //根据bridgeID查询巡检计划
        PageInfo<ProblemEvent> pageInfo = problemEventService.getProblemEventByRecordID(recordID, pageNum, pageSize);
        PageResult<ProblemEvent> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 根据Id查询问题事件
     */
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        ProblemEvent problemEvent = problemEventService.getProblemEventByID(id);
        return new Result(ResultCode.SUCCESS , problemEvent);
    }

    /**
     * 保存问题事件
     */
    @RequestMapping(value = "/problemEvent/import",method = RequestMethod.POST)
    public Result save(@RequestBody ProblemEvent problemEvent) {
        problemEventService.save(problemEvent);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id修改问题事件
     */
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody ProblemEvent inspectionPlan){
        //调用Service更新
        problemEventService.update(id , inspectionPlan);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除问题事件
     */
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        problemEventService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}