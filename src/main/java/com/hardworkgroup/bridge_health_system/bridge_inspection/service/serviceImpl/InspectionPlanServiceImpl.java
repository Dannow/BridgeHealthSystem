package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.activiti.service.IActFlowCustomService;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionPlanDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionPlanService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimplePlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * (TPatrolPlan)表服务实现类
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
@Service
@Slf4j
public class InspectionPlanServiceImpl implements InspectionPlanService, IActFlowCustomService {

    @Resource
    InspectionPlanDao inspectionPlanDao;

    @Override
    public PageInfo<InspectionPlan> findAll(int pageNum, int pageSize) {
        Page<InspectionPlan> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllInspectionPlan();
        return new PageInfo<>(inspectionPlans,5);
    }

    @Override
    public List<SimplePlan> findAllByCheckInStatus(Integer userID, Integer inspectionCheckInStatus) {
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllByCheckInStatus(userID,inspectionCheckInStatus);
        List<SimplePlan> simplePlans = new ArrayList<>();
        for (InspectionPlan inspectionPlan : inspectionPlans) {
            simplePlans.add(new SimplePlan(inspectionPlan));
        }
        return simplePlans;
    }

    @Override
    public PageInfo<InspectionPlan> getPlanByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<InspectionPlan> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllByBridgeID(bridgeID);
        return new PageInfo<>(inspectionPlans,5);
    }

    @Override
    public List<SimplePlan> getPlanByBridgeID(Integer bridgeID) {
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllByBridgeID(bridgeID);
        List<SimplePlan> simplePlans = new ArrayList<>();
        for (InspectionPlan inspectionPlan : inspectionPlans) {
            simplePlans.add(new SimplePlan(inspectionPlan));
        }
        return simplePlans;
    }

    @Override
    public List<SimplePlan> getPlanByUserID(Integer userID) {
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllByUserID(userID);
        List<SimplePlan> simplePlans = new ArrayList<>();
        for (InspectionPlan inspectionPlan : inspectionPlans) {
            simplePlans.add(new SimplePlan(inspectionPlan));
        }
        return simplePlans;
    }

    @Override
    public InspectionPlan getPlanByCheckTime(Integer userID, String bridgeName, Date checkTime) {
        return inspectionPlanDao.getPlanByBridgeNameAndCheckTime(userID,bridgeName,checkTime);
    }

    @Override
    public Integer save(InspectionPlan inspectionPlan) {
        //调用dao保存用户
        return inspectionPlanDao.insertByKey(inspectionPlan);
    }

    @Override
    public InspectionPlan getPlanByID(String InspectionPlanID) {
        return  inspectionPlanDao.getPlanByID(InspectionPlanID);
    }

    @Override
    public void update(String InspectionPlanID, InspectionPlan inspectionPlan) {
        InspectionPlan tempPlan = inspectionPlanDao.getPlanByID(InspectionPlanID);
        tempPlan.setUserID(inspectionPlan.getUserID());
        tempPlan.setBridgeID(inspectionPlan.getBridgeID());
        tempPlan.setInspectionStartTime(inspectionPlan.getInspectionStartTime());
        tempPlan.setInspectionEndTime(inspectionPlan.getInspectionEndTime());
        tempPlan.setInspectionCompletionStatus(inspectionPlan.getInspectionCompletionStatus());
        tempPlan.setInspectionCheckInStatus(inspectionPlan.getInspectionCheckInStatus());
        tempPlan.setInspectionPlanTitle(inspectionPlan.getInspectionPlanTitle());
        inspectionPlanDao.updateByKey(tempPlan);
    }

    @Override
    public void delete(String InspectionPlanID) {
        inspectionPlanDao.deleteByKey(InspectionPlanID);
    }

    @Override
    public Map<String, Object> setvariables(Integer id,Integer userID) {
        InspectionPlan inspectionPlan = this.getPlanByID(id.toString());
        //设置流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("assignee0",userID.toString());
        variables.put("assignee1",inspectionPlan.getUserID().toString());
        /*variables.put("assignee2",3);
        variables.put("assignee3",4);*/
        variables.put("inspectionPlan",inspectionPlan);
        return variables;
    }

    @Override
    public void startRunTask(Integer id) {
        inspectionPlanDao.startTask(id);
    }

    @Override
    public void endRunTask(Integer id) {
        inspectionPlanDao.endTask(id);
    }
}