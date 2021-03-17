package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
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
public interface ProblemEventDao extends Mapper<ProblemEvent> {

    List<ProblemEvent> selectAllProblemEvent();

    List<ProblemEvent> selectAllByRecordID(Integer inspectionRecordID);

    void insertByKey(ProblemEvent problemEvent);

    ProblemEvent getProblemEventByID(String id);

    void deleteByKey(String problemEventID);

    void updateByKey(ProblemEvent tempProblemEvent);
}