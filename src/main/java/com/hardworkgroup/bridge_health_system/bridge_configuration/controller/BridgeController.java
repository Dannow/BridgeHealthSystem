package com.hardworkgroup.bridge_health_system.bridge_configuration.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.BridgeServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.entity.Bridge;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_configuration.response.BridgeSimpleResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (BridgeController)表控制层
 *
 * @author makejava
 * @since 2020-12-27 10:35:05
 */
//解决跨域
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bridgeConfiguration")
public class BridgeController {
    /**
     * 服务对象
     */
    @Autowired
    private BridgeServiceImpl bridgeService;

    /**
     * 获取所有桥梁列表
     * @return 桥梁结果
     */
    @RequestMapping(value = "/bridges" , method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String,String > map){
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<Bridge> pageInfo = bridgeService.findAll(pageNum, pageSize);
        PageResult<Bridge> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 获取所有桥梁列表
     * @return 桥梁结果
     */
    @RequestMapping(value = "/bridges" , method = RequestMethod.GET)
    public Result findAll(){
        return new Result(ResultCode.SUCCESS,bridgeService.findAll());
    }

    /**
     * 保存桥梁
     */
    @RequestMapping(value = "/bridge/import",method = RequestMethod.POST)
    public Result save(@RequestBody Bridge bridge) {
        bridgeService.save(bridge);
        return new  Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询桥梁
     */
    @RequestMapping(value = "/bridge/{id}" , method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加planID
        Bridge bridge = bridgeService.getSensorByID(id);
        return new Result(ResultCode.SUCCESS , bridge);
    }

    /**
     * 根据Id修改桥梁
     */
    @RequestMapping(value = "/bridge/{id}" , method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id , @RequestBody Bridge bridge){
        //调用Service更新
        bridgeService.update(id , bridge);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除桥梁
     */
    @RequestMapping(value = "/bridge/{id}" , method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        bridgeService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}