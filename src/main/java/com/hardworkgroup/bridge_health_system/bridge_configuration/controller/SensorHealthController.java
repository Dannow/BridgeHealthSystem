package com.hardworkgroup.bridge_health_system.bridge_configuration.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorHealthServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.SensorHealth;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (SensorHealthController)表控制层
 *
 * @author hui
 * @since 2021-03-27 10:35:05
 */
//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bridgeConfiguration")
public class SensorHealthController {

    @Resource
    SensorHealthServiceImpl sensorHealthServiceImpl;

    /**
     * 根据桥梁ID获取所有传感器健康状况
     * @return 桥梁结果
     */
    @RequestMapping(value = "/sensorHealths/bridgeID/{bridgeID}" , method = RequestMethod.POST)
    public Result findAllByBridgeID(@PathVariable(value = "bridgeID") Integer bridgeID, @RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<SensorHealth> pageInfo = sensorHealthServiceImpl.findAllByBridgeID(bridgeID,pageNum, pageSize);
        PageResult<SensorHealth> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 获取所有传感器健康状况
     * @return 桥梁结果
     */
    @RequestMapping(value = "/sensorHealths" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<SensorHealth> pageInfo = sensorHealthServiceImpl.findAll(pageNum, pageSize);
        PageResult<SensorHealth> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 保存传感器健康状况
     */
    @RequestMapping(value = "/sensorHealth/import",method = RequestMethod.POST)
    public Result save(@RequestBody SensorHealth sensorHealth) {
        sensorHealthServiceImpl.save(sensorHealth);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询传感器健康状况
     */
    @RequestMapping(value = "/sensorHealth/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        SensorHealth sensorHealth = sensorHealthServiceImpl.getSensorByID(id);
        return new Result(ResultCode.SUCCESS , sensorHealth);
    }

    /**
     * 根据Id修改传感器健康状况
     */
    @RequestMapping(value = "/sensorHealth/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody SensorHealth sensorHealth){
        //调用Service更新
        sensorHealthServiceImpl.update(sensorHealth);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除传感器健康状况
     */
    @RequestMapping(value = "/sensorHealth/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        sensorHealthServiceImpl.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
