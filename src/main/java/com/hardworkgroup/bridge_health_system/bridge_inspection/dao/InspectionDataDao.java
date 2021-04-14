package com.hardworkgroup.bridge_health_system.bridge_inspection.dao;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
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
public interface InspectionDataDao extends Mapper<InspectionData> {

    List<InspectionData> selectAllInspectionData();

    List<InspectionData> selectAllByRecordID(Integer inspectionRecordID);

    void insertByKey(InspectionData inspectionData);

    InspectionData getInspectionDataByID(String id);

    void deleteByKey(String inspectionDataID);

    void updateByKey(InspectionData tempInspectionData);
}