package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (TPatrolRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface InspectionRecordDao extends Mapper<InspectionRecord> {

    InspectionRecord selectInspectionRecord(int inspectionID);
    InspectionRecord addInspectionData(InspectionRecord TPatrolRecord);

    List<InspectionRecord> selectAllInspectionRecord();

    void insertByKey(InspectionRecord inspectionRecord);

    InspectionRecord getRecordByID(String id);

    void updateByKey(InspectionRecord inspectionRecord);

    void deleteByKey(String inspectionRecordID);

    List<InspectionRecord> selectAllByPlanID(Integer inspectionPlanID);

    List<InspectionRecord> selectAllByBridgeID(Integer bridgeID);

    List<InspectionRecord> selectAllByUserID(Integer userID);
}