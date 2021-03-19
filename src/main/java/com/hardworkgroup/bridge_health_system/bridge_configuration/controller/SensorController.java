package com.hardworkgroup.bridge_health_system.bridge_configuration.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Sensor;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (SensorController)表控制层
 *
 * @author makejava
 * @since 2020-12-27 10:35:05
 */
//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bridgeConfiguration")
public class SensorController {
    /**
     * 服务对象
     */
    @Autowired
    private SensorServiceImpl sensorService;

    /**
     * 获取所有传感器列表
     * @return 传感器结果
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
     * 保存传感器
     */
    @RequestMapping(value = "/sensor/import",method = RequestMethod.POST)
    public Result save(@RequestBody Sensor sensor) {
        sensorService.save(sensor);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询传感器
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        Sensor sensor = sensorService.getSensorByID(id);
        return new Result(ResultCode.SUCCESS , sensor);
    }

    /**
     * 根据Id修改传感器
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody Sensor sensor){
        //调用Service更新
        sensorService.update(sensor);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除传感器
     */
    @RequestMapping(value = "/sensor/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        sensorService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}