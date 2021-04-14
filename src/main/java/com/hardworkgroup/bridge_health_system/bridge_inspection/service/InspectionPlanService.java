package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimplePlan;

import java.util.Date;
import java.util.List;

/**
 * (TPatrolPlan)表服务接口
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
public interface InspectionPlanService {


    PageInfo<InspectionPlan> findAll(int pageNum, int pageSize);

    Integer save(InspectionPlan inspectionPlan);

    InspectionPlan getPlanByID(String id);

    void update(String id, InspectionPlan inspectionPlan);

    void delete(String InspectionPlanID);

    List<SimplePlan> findAllByCheckInStatus(Integer userID, Integer inspectionCheckInStatus);

    PageInfo<InspectionPlan> getPlanByBridgeID(Integer bridgeID, int pageNum, int pageSize);

    List<SimplePlan> getPlanByBridgeID(Integer bridgeID);

    List<SimplePlan> getPlanByUserID(Integer userID);

    InspectionPlan getPlanByCheckTime(Integer userID,String bridgeName, Date checkTime);
}