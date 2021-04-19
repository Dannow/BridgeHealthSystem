package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventPictureDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.ProblemEventServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleEvent;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private ProblemEventServiceImpl problemEventService;

    @Resource
    ProblemEventPictureDao problemEventPictureDao;
    /**
     * 获取所有问题事件列表
     * @return 巡检计划结果
     */
    @Transactional
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvents" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<ProblemEvent> pageInfo = problemEventService.findAll(pageNum, pageSize);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : pageInfo.getList()) {
            List<ProblemEventPicture> problemEventPictures =  problemEventPictureDao.selectAllByEventID(problemEvent.getProblemEventID());
            problemEvent.setProblemEventPictures(problemEventPictures);
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        PageResult<SimpleEvent> pageResult = new PageResult<>(pageInfo.getTotal(), simpleEvents);
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 获取所有问题事件列表
     * @return 巡检计划结果
     */
    @Transactional
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvents" , method = RequestMethod.GET)
    public Result findAll(){
        List<SimpleEvent> simpleEvents = problemEventService.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("rows",simpleEvents);
        return new Result(ResultCode.SUCCESS , map);
    }

    /**
     * 根据记录Id查询问题事件
     */
    @Transactional
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvent/recordID/{recordID}" , method = RequestMethod.POST)
    public Result findByRecordID(@PathVariable(value = "recordID") Integer recordID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        //根据bridgeID查询巡检计划
        PageInfo<ProblemEvent> pageInfo = problemEventService.getProblemEventByRecordID(recordID, pageNum, pageSize);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : pageInfo.getList()) {
            List<ProblemEventPicture> problemEventPictures =  problemEventPictureDao.selectAllByEventID(problemEvent.getProblemEventID());
            problemEvent.setProblemEventPictures(problemEventPictures);
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        PageResult<SimpleEvent> pageResult = new PageResult<>(pageInfo.getTotal(), simpleEvents);
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 根据桥梁Id查询问题事件
     */
    @Transactional
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvent/bridgeID/{bridgeID}" , method = RequestMethod.POST)
    public Result findByBridgeId(@PathVariable(value = "bridgeID") Integer bridgeID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<ProblemEvent> pageInfo = problemEventService.findAllByBridgeID(bridgeID, pageNum, pageSize);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : pageInfo.getList()) {
            List<ProblemEventPicture> problemEventPictures =  problemEventPictureDao.selectAllByEventID(problemEvent.getProblemEventID());
            problemEvent.setProblemEventPictures(problemEventPictures);
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        PageResult<SimpleEvent> pageResult = new PageResult<>(pageInfo.getTotal(), simpleEvents);
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 手机端根据桥梁Id查询问题事件
     */
    @Transactional
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvent/bridgeID/{bridgeID}" , method = RequestMethod.GET)
    public Result findByBridgeId(@PathVariable(value = "bridgeID") Integer bridgeID){
        List<SimpleEvent> simpleEvents = problemEventService.findAllByBridgeID(bridgeID);
        Map<String, Object> map = new HashMap<>();
        map.put("rows",simpleEvents);
        return new Result(ResultCode.SUCCESS , map);
    }

    /**
     * 根据Id查询问题事件
     */
    @RequiresPermissions(value = "MENU-INSPECTION-EVENTS")
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        ProblemEvent problemEvent = problemEventService.getProblemEventByID(id);
        SimpleEvent simpleEvent = new SimpleEvent(problemEvent);
        return new Result(ResultCode.SUCCESS , simpleEvent);
    }

    /**
     * 保存问题事件
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-ADD")
    @RequestMapping(value = "/problemEvent/import",method = RequestMethod.POST)
    public Result save(@RequestBody ProblemEvent problemEvent) {
        problemEventService.save(problemEvent);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id修改问题事件
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-UPDATE")
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody ProblemEvent inspectionPlan){
        //调用Service更新
        problemEventService.update(id , inspectionPlan);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除问题事件
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-DELETE")
    @RequestMapping(value = "/problemEvent/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        problemEventService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}