package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleRecord;

import java.util.List;

/**
 * (TPatrolRecord)表服务接口
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
public interface InspectionRecordService {

    InspectionRecord selectInspectionRecord(int inspectionID);

    void save(InspectionRecord inspectionRecord);

    PageInfo<InspectionRecord> findAll(int pageNum, int pageSize);

    void update(InspectionRecord inspectionRecord);

    InspectionRecord getRecordByID(String id);

    void delete(String id);

    PageInfo<InspectionRecord> findAllByPlanID(Integer planID, int pageNum, int pageSize);

    PageInfo<InspectionRecord> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize);

    List<SimpleRecord> findAllByBridgeID(Integer bridgeID);

    List<SimpleRecord> findAllByUserID(Integer userId);

    void insert(InspectionRecord inspectionRecord);
}