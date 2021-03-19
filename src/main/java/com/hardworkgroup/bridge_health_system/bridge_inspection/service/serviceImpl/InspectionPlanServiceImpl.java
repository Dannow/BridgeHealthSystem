package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionPlanDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionPlanService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (TPatrolPlan)表服务实现类
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
@Service
@Slf4j
public class InspectionPlanServiceImpl implements InspectionPlanService {

    @Autowired
    InspectionPlanDao inspectionPlanDao;

    @Override
    public PageInfo<InspectionPlan> findAll(int pageNum, int pageSize) {
        Page<InspectionPlan> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllInspectionPlan();
        PageInfo<InspectionPlan> pageInfo = new PageInfo<>(inspectionPlans,5);

        return pageInfo;
    }

    @Override
    public PageInfo<InspectionPlan> getPlanByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<InspectionPlan> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionPlan> inspectionPlans =  inspectionPlanDao.selectAllByBridgeID(bridgeID);
        PageInfo<InspectionPlan> pageInfo = new PageInfo<>(inspectionPlans,5);
        return pageInfo;
    }

    @Override
    public void save(InspectionPlan inspectionPlan) {
        //调用dao保存用户
        inspectionPlanDao.insertByKey(inspectionPlan);
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

}