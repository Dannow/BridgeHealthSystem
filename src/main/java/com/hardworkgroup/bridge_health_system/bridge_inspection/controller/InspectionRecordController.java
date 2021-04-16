package com.hardworkgroup.bridge_health_system.bridge_inspection.controller;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_configuration.service.serviceImpl.SensorServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionDataDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionDataServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionPlanServiceImpl;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl.InspectionRecordServiceImpl;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleRecord;
import com.hardworkgroup.bridge_health_system.system_common.controller.BaseController;
import com.hardworkgroup.bridge_health_system.system_common.entity.PageResult;
import com.hardworkgroup.bridge_health_system.system_common.entity.Result;
import com.hardworkgroup.bridge_health_system.system_common.entity.ResultCode;
import com.hardworkgroup.bridge_health_system.system_common.utils.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.expr.Component;
import org.apache.commons.io.output.AppendableOutputStream;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
public class InspectionRecordController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private InspectionRecordServiceImpl inspectionRecordService;

    @Resource
    private InspectionPlanServiceImpl inspectionPlanService;

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<InspectionRecord> pageInfo = inspectionRecordService.findAll(pageNum, pageSize);
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        for (InspectionRecord inspectionRecord : pageInfo.getList()) {
            //inspectionRecord.setInspectionData(new HashSet<>(inspectionDataDao.selectAllByRecordID(inspectionRecord.getInspectionRecordID())));
            simpleRecords.add(new SimpleRecord(inspectionRecord));
        }
        PageResult<SimpleRecord> pageResult = new PageResult<>(pageInfo.getTotal(),simpleRecords);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record/planID/{planID}", method = RequestMethod.POST)
    public Result findByPlanID(@PathVariable(value = "planID") Integer planID,@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<InspectionRecord> pageInfo = inspectionRecordService.findAllByPlanID(planID,pageNum, pageSize);
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        for (InspectionRecord inspectionRecord : pageInfo.getList()) {
            //inspectionRecord.setInspectionData(new HashSet<>(inspectionDataDao.selectAllByRecordID(inspectionRecord.getInspectionRecordID())));
            simpleRecords.add(new SimpleRecord(inspectionRecord));
        }
        PageResult<SimpleRecord> pageResult = new PageResult<>(pageInfo.getTotal(),simpleRecords);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据bridgeID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record/bridgeID/{bridgeID}", method = RequestMethod.POST)
    public Result findByBridgeID(@PathVariable(value = "bridgeID") Integer bridgeID,@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt((String) map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageInfo<InspectionRecord> pageInfo = inspectionRecordService.findAllByBridgeID(bridgeID,pageNum, pageSize);
        List<SimpleRecord> simpleRecords = new ArrayList<>();
        for (InspectionRecord inspectionRecord : pageInfo.getList()) {
            //inspectionRecord.setInspectionData(new HashSet<>(inspectionDataDao.selectAllByRecordID(inspectionRecord.getInspectionRecordID())));
            simpleRecords.add(new SimpleRecord(inspectionRecord));
        }
        PageResult<SimpleRecord> pageResult = new PageResult<>(pageInfo.getTotal(),simpleRecords);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 手机端根据bridgeID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record/bridgeID/{bridgeID}", method = RequestMethod.GET)
    public Result findByBridgeID(@PathVariable(value = "bridgeID") Integer bridgeID) {
        List<SimpleRecord> simpleRecords = inspectionRecordService.findAllByBridgeID(bridgeID);
        Map<String,Object> map =new HashMap<>();
        map.put("rows",simpleRecords);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 手机端根据userID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public Result findByUserID() {
        List<SimpleRecord> simpleRecords = inspectionRecordService.findAllByUserID(this.userId);
        Map<String,Object> map =new HashMap<>();
        map.put("rows",simpleRecords);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 手机端保存巡检记录
     */
    @RequiresPermissions(value = "POINT-INSPECTION-RECORD-ADD")
    @RequestMapping(value = "/bridgeName/{bridgeName}/record/add", method = RequestMethod.POST)
    public Result save(@PathVariable(value = "bridgeName") String bridgeName, @RequestBody Map<String,Object> map) throws Exception {
        InspectionRecord inspectionRecord = BeanMapUtils.mapToBean(map,InspectionRecord.class);
        InspectionPlan inspectionPlan = inspectionPlanService.getPlanByCheckTime(this.userId, bridgeName, inspectionRecord.getInspectionTime());
        inspectionRecord.setInspectionPlanID(inspectionPlan.getInspectionPlanID());
        log.info(inspectionRecord.getInspectionPlanID().toString());
        inspectionRecordService.insert(inspectionRecord);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 保存巡检记录
     */
    @RequiresPermissions(value = "POINT-INSPECTION-RECORD-ADD")
    @RequestMapping(value = "/record/import", method = RequestMethod.POST)
    public Result save(@RequestBody InspectionRecord inspectionRecord){
        inspectionRecordService.save(inspectionRecord);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id查询巡检记录
     */
    @RequiresPermissions(value = "MENU-INSPECTION-RECORDS")
    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        //添加roleIds(用户已经具有的角色id数组)
        InspectionRecord inspectionRecord = inspectionRecordService.getRecordByID(id);
        SimpleRecord simpleRecord = new SimpleRecord(inspectionRecord);
        return new Result(ResultCode.SUCCESS, simpleRecord);
    }

    /**
     * 根据Id修改巡检记录
     */
    @RequiresPermissions(value = "POINT-INSPECTION-RECORD-UPDATE")
    @RequestMapping(value = "/record/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody InspectionRecord inspectionRecord) {
        //调用Service更新
        inspectionRecordService.update(inspectionRecord);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除巡检记录
     */
    @RequiresPermissions(value = "POINT-INSPECTION-RECORD-DELETE")
    @RequestMapping(value = "/record/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        inspectionRecordService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}