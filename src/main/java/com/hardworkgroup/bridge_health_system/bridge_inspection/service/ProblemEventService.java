package com.hardworkgroup.bridge_health_system.bridge_inspection.service;


import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;

/**
 * (TPatrolRecord)表服务接口
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
public interface ProblemEventService {

    void save(ProblemEvent problemEvent);

    PageInfo<ProblemEvent> findAll(int pageNum, int pageSize);

    PageInfo<ProblemEvent> getProblemEventByRecordID(Integer recordID, int pageNum, int pageSize);

    ProblemEvent getProblemEventByID(String id);

    void delete(String problemEventID);

    void update(String id, ProblemEvent problemEvent);
}