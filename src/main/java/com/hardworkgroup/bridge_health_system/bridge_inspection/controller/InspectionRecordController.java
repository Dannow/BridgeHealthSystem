package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionRecordServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * (InspectionRecordController)表控制层
 *
 * @author hui
 * @since 2022-12-31 18:08:33
 */
@Slf4j
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/bridgeInspection/inspection")
public class InspectionRecordController {
    /**
     * 服务对象
     */
    @Autowired
    private InspectionRecordServiceImpl inspectionRecordService;

    /**
     * 获取所有巡检记录列表
     *
     * @return 巡检记录结果
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<InspectionRecord> pageInfo = inspectionRecordService.findAll(pageNum, pageSize);
        PageResult<InspectionRecord> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 保存巡检记录
     */
    @RequestMapping(value = "/record/import", method = RequestMethod.POST)
    public Result addInspectionRecord(@RequestBody InspectionRecord inspectionRecord) {
        inspectionRecordService.save(inspectionRecord);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询巡检记录
     */
    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        //添加roleIds(用户已经具有的角色id数组)
        InspectionRecord inspectionRecord = inspectionRecordService.getRecordByID(id);
        return new Result(ResultCode.SUCCESS, inspectionRecord);
    }

    /**
     * 根据Id修改巡检记录
     */
    @RequestMapping(value = "/record/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody InspectionRecord inspectionRecord) {
        //调用Service更新
        inspectionRecordService.update(id, inspectionRecord);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检记录
     */
    @RequestMapping(value = "/record/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        inspectionRecordService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }














    @RequestMapping(value = "/inspection/record/{structID}",method = RequestMethod.GET)
    public Result getInspectionRecord(@PathVariable("structID") int inspectionID) {
        InspectionRecord inspectionRecord =  inspectionRecordService.selectInspectionRecord(inspectionID);
        return new Result(ResultCode.SUCCESS);
    }
}