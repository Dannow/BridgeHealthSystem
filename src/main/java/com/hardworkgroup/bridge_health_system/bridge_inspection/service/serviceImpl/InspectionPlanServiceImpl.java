package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionPlanDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionPlanService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
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
public class InspectionPlanServiceImpl implements InspectionPlanService {

    @Autowired
    InspectionPlanDao inspectionPlanDao;

    @Override
    public PageInfo<InspectionPlan> findAll(int pageNum, int pageSize) {
        Page<InspectionPlan> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionPlan> inspectionRecords =  inspectionPlanDao.selectAllInspectionPlan();
        PageInfo<InspectionPlan> pageInfo = new PageInfo<>(inspectionRecords,5);

        /*for (InspectionRecord inspectionRecord : pageInfo.getList()) {
            Set<Role> roles = roleDao.getRoleByUserId(user.getUserID());
            user.setRoles(roles);
        }*/
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
        tempPlan.setInspectionStartTime(inspectionPlan.getInspectionStartTime());
        tempPlan.setInspectionEndTime(inspectionPlan.getInspectionEndTime());
        tempPlan.setInspectionCompletionStatus(inspectionPlan.getInspectionCompletionStatus());
        tempPlan.setInspectionCheckInStatus(inspectionPlan.getInspectionCheckInStatus());
        inspectionPlanDao.updateByKey(tempPlan);
    }

    @Override
    public void delete(String InspectionPlanID) {
        inspectionPlanDao.deleteByKey(InspectionPlanID);
    }
}