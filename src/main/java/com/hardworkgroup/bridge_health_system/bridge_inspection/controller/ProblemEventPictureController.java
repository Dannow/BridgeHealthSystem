package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.ProblemEventPictureService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (ProblemEventPictureController)表控制层
 *
 * @author makejava
 * @since 2021-01-16 18:11:59
 */
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class ProblemEventPictureController {
    /**
     * 服务对象
     */
    @Resource
    private ProblemEventPictureService problemEventPictureService;

    /**
     * 获取所有问题事件图片列表
     * @return 巡检计划结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-EVENT-PICTURES")
    @RequestMapping(value = "/problemEventPictures" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<ProblemEventPicture> pageInfo = problemEventPictureService.findAll(pageNum, pageSize);
        PageResult<ProblemEventPicture> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 根据问题事件Id查询问题事件图片
     */
    @RequiresPermissions(value = "MENU-INSPECTION-EVENT-PICTURES")
    @RequestMapping(value = "/problemEventPicture/problemEventID/{problemEventID}" , method = RequestMethod.POST)
    public Result findByRecordID(@PathVariable(value = "problemEventID") Integer problemEventID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        //根据bridgeID查询巡检计划
        PageInfo<ProblemEventPicture> pageInfo = problemEventPictureService.findAllByEventID(problemEventID, pageNum, pageSize);
        PageResult<ProblemEventPicture> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS , pageResult);
    }

    /**
     * 根据记录Id查询问题事件图片
     */
    /*@RequestMapping(value = "/problemEvent/bridgeID/{bridgeID}" , method = RequestMethod.POST)
    public Result findByBridgeId(@PathVariable(value = "bridgeID") Integer bridgeID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<ProblemEventPicture> pageInfo = problemEventPictureService.findAllByBridgeID(bridgeID, pageNum, pageSize);
        PageResult<ProblemEventPicture> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS , pageResult);
    }*/

    /**
     * 根据Id查询问题事件图片
     */
    @RequiresPermissions(value = "MENU-INSPECTION-EVENT-PICTURES")
    @RequestMapping(value = "/problemEventPicture/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        ProblemEventPicture problemEventPicture = problemEventPictureService.getProblemEventPictureByID(id);
        return new Result(ResultCode.SUCCESS , problemEventPicture);
    }

    /**
     * 保存问题事件图片
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-PICTURE-ADD")
    @RequestMapping(value = "/problemEventPicture/import",method = RequestMethod.POST)
    public Result save(@RequestBody ProblemEventPicture problemEventPicture) {
        problemEventPictureService.save(problemEventPicture);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id修改问题事件图片
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-PICTURE-UPDATE")
    @RequestMapping(value = "/problemEventPicture/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody ProblemEventPicture problemEventPicture){
        //调用Service更新
        problemEventPictureService.update(id , problemEventPicture);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除问题事件图片
     */
    @RequiresPermissions(value = "POINT-INSPECTION-EVENT-PICTURE-DELETE")
    @RequestMapping(value = "/problemEventPicture/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        problemEventPictureService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}