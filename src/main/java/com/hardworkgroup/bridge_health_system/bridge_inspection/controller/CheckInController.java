package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.CheckInServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionPlanServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.CheckIn;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CheckInController)表控制层
 *
 * @author hui
 * @since 2021-03-26 18:11:59
 */
//解决跨域
@CrossOrigin
@Slf4j
@RestController
@Api(tags = "巡检打卡口")
@RequestMapping("/bridgeInspection/inspection")
public class CheckInController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private CheckInServiceImpl checkInService;

    @Resource
    private InspectionPlanServiceImpl inspectionPlanService;

    /**
     * 获取所有巡检打卡列表
     * @return 巡检打卡结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-CHECKINS")
    @RequestMapping(value = "/checkin" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<CheckIn> pageInfo = checkInService.findAll(pageNum, pageSize);
        PageResult<CheckIn> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据用户Id查询巡检打卡
     */
    @RequiresPermissions(value = "MENU-INSPECTION-CHECKINS")
    @RequestMapping(value = "/checkin/user/{userID}" , method = RequestMethod.POST)
    public Result findByBridgeId(@PathVariable(value = "userID") Integer userID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        //根据bridgeID查询巡检计划
        PageInfo<CheckIn> pageInfo = checkInService.findAllByUserID(userID, pageNum, pageSize);
        PageResult<CheckIn> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 根据Id查询巡检打卡
     */
    @RequiresPermissions(value = "MENU-INSPECTION-CHECKINS")
    @RequestMapping(value = "/checkin/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        CheckIn checkIn = checkInService.getCheckInByID(id);
        return new Result(ResultCode.SUCCESS , checkIn);
    }

    /**
     * 手机端保存巡检打卡
     */
    @RequiresPermissions(value = "POINT-INSPECTION-CHECKIN-ADD")
    @RequestMapping(value = "/bridgeName/{bridgeName}/checkin/add",method = RequestMethod.POST)
    @Transactional
    public Result save(@PathVariable(value = "bridgeName") String  bridgeName, @RequestBody CheckIn checkIn) {
        InspectionPlan inspectionPlan = inspectionPlanService.getPlanByCheckTime(this.userId,bridgeName,checkIn.getCheckTime());
        if(inspectionPlan!=null){
            checkIn.setInspectionPlanID(inspectionPlan.getInspectionPlanID());
        }
        else {
            return new Result(ResultCode.CHECKERROR);
        }
        checkIn.setUserID(this.userId);
        checkInService.save(checkIn);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 保存巡检打卡
     */
    @RequiresPermissions(value = "POINT-INSPECTION-CHECKIN-ADD")
    @RequestMapping(value = "/checkin/import",method = RequestMethod.POST)
    @Transactional
    public Result save(@RequestBody CheckIn checkIn) {
        checkInService.save(checkIn);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id修改巡检打卡
     */
    @RequiresPermissions(value = "POINT-INSPECTION-CHECKIN-UPDATE")
    @RequestMapping(value = "/checkin/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody CheckIn checkIn){
        //调用Service更新
        checkInService.update(checkIn);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检打卡
     */
    @RequiresPermissions(value = "POINT-INSPECTION-CHECKIN-DELETE")
    @RequestMapping(value = "/checkin/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        checkInService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}