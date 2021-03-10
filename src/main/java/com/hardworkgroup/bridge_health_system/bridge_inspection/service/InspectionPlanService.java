package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;


import java.util.List;

/**
 * (TPatrolPlan)表服务接口
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
public interface InspectionPlanService {


    PageInfo<InspectionPlan> findAll(int pageNum, int pageSize);

    void save(InspectionPlan inspectionPlan);

    InspectionPlan getPlanByID(String id);

    void update(String id, InspectionPlan inspectionPlan);

    void delete(String InspectionPlanID);
}