package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleRecord;

/**
 * (TPatrolRecord)表服务接口
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
public interface InspectionRecordService {

    InspectionRecord selectInspectionRecord(int inspectionID);

    void save(InspectionRecord inspectionRecord);

    PageInfo<SimpleRecord> findAll(int pageNum, int pageSize);

    void update(InspectionRecord inspectionRecord);

    InspectionRecord getRecordByID(String id);

    void delete(String id);

    PageInfo<SimpleRecord> findAllByPlanID(Integer planID, int pageNum, int pageSize);

    PageInfo<SimpleRecord> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize);
}