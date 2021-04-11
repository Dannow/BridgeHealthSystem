package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * (TPatrolPlan)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
@Repository
public interface InspectionPlanDao extends Mapper<InspectionPlan> {

    List<InspectionPlan> selectAllInspectionPlan();

    Integer insertByKey(InspectionPlan inspectionPlan);

    InspectionPlan getPlanByID(String inspectionPlanID);

    void updateByKey(InspectionPlan tempPlan);

    void deleteByKey(String inspectionPlanID);

    List<InspectionPlan> selectAllByBridgeID(Integer bridgeID);

    @Update("update inspection_plan set inspectionCompletionStatus=0 where inspectionPlanID=#{inspectionPlanID}")
    int startTask(Integer inspectionPlanID);

    @Update("update inspection_plan set inspectionCompletionStatus=1 where inspectionPlanID=#{inspectionPlanID}")
    int endTask(Integer inspectionPlanID);

    List<InspectionPlan> selectAllByUserID(Integer userID);

    InspectionPlan getPlanByBridgeNameAndCheckTime(Integer userID, String bridgeName, Date checkTime);
}