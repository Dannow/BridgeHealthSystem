package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionDataDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionDataService;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionRecordServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleRecord;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * (InspectionDataController)表控制层
 *
 * @author hui
 * @since 2022-12-31 18:08:33
 */
@Slf4j
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class InspectionDataController {

    /**
     * 服务对象
     */

    @Resource
    private InspectionDataServiceImpl inspectionDataService;

    /**
     * 获取所有巡检数据列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-DATA")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        int pageSize = Integer.parseInt(map.get("pageSize"));
        PageInfo<InspectionData> pageInfo = inspectionDataService.findAll(pageNum, pageSize);
        List<SimpleData> simpleDatum = new ArrayList<>();
        for (InspectionData inspectionData : pageInfo.getList()) {
            simpleDatum.add(new SimpleData(inspectionData));
        }
        PageResult<SimpleData> pageResult = new PageResult<>(pageInfo.getTotal(),simpleDatum);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据recordID获取所有巡检数据列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-DATA")
    @RequestMapping(value = "/data/recordID/{recordID}", method = RequestMethod.POST)
    public Result findByRecordID(@PathVariable(value = "recordID") Integer recordID,@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        int pageSize = Integer.parseInt(map.get("pageSize"));
        PageInfo<InspectionData> pageInfo = inspectionDataService.findAllByRecordID(recordID,pageNum, pageSize);
        List<SimpleData> simpleDatum = new ArrayList<>();
        for (InspectionData inspectionData : pageInfo.getList()) {
            simpleDatum.add(new SimpleData(inspectionData));
        }
        PageResult<SimpleData> pageResult = new PageResult<>(pageInfo.getTotal(),simpleDatum);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 保存巡检数据
     */
    @Transactional
    @RequiresPermissions(value = "POINT-INSPECTION-DATUM-ADD")
    @RequestMapping(value = "/data/import", method = RequestMethod.POST)
    public Result addInspectionData(@RequestBody InspectionData inspectionData){
        inspectionDataService.save(inspectionData);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询巡检数据
     */
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        //添加roleIds(用户已经具有的角色id数组)
        InspectionData inspectionData = inspectionDataService.getInspectionDataByID(id);
        SimpleData simpleData = new SimpleData(inspectionData);
        return new Result(ResultCode.SUCCESS, simpleData);
    }

    /**
     * 根据Id修改巡检数据
     */
    @Transactional
    @RequiresPermissions(value = "POINT-INSPECTION-DATUM-UPDATE")
    @RequestMapping(value = "/data/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody InspectionData inspectionData){
        //调用Service更新
        inspectionDataService.update(inspectionData);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检数据
     */
    @Transactional
    @RequiresPermissions(value = "POINT-INSPECTION-DATUM-DELETE")
    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        inspectionDataService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}