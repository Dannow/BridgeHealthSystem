package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionPlan;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TPatrolPlan)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-16 18:11:58
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface InspectionPlanDao extends Mapper<InspectionPlan> {

    List<InspectionPlan> selectAllInspectionPlan();

    void insertByKey(InspectionPlan inspectionPlan);

    InspectionPlan getPlanByID(String inspectionPlanID);

    void updateByKey(InspectionPlan tempPlan);

    void deleteByKey(String inspectionPlanID);
}