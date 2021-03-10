package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.Sensor;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * (SensorController)表控制层
 *
 * @author makejava
 * @since 2020-12-27 10:35:05
 */
@Controller
@Slf4j
@RequestMapping("/bridgeInspection")
public class SensorController {
    /**
     * 服务对象
     */
    @Autowired
    private SensorServiceImpl sensorService;

    //@RequestMapping("/getallsensors")
    //@RequestMapping("/last-data")
    //@RequestMapping("/alarm_image")
    /**
     * 获取所有巡检计划列表
     * @return 巡检记录结果
     */
    @RequestMapping(value = "/sensors" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<Sensor> pageInfo = sensorService.findAll(pageNum, pageSize);
        PageResult<Sensor> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 保存巡检记录
     */
    @RequestMapping(value = "/sensor/import",method = RequestMethod.POST)
    public Result save(@RequestBody Sensor sensor) {
        sensorService.save(sensor);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询巡检记录
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        Sensor sensor = sensorService.getSensorByID(id);
        return new Result(ResultCode.SUCCESS , sensor);
    }

    /**
     * 根据Id修改巡检记录
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody Sensor sensor){
        //调用Service更新
        sensorService.update(id , sensor);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检记录
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        sensorService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}