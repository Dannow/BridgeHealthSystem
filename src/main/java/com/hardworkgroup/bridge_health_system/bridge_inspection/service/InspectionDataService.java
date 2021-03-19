package com.hardworkgroup.bridge_health_system.bridge_inspection.service;

import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;

/**
 * (InspectionData)表服务接口
 *
 * @author bjh
 * @since 2021-03-19 18:08:33
 */
public interface InspectionDataService {

    PageInfo<InspectionData> findAll(int pageNum, int pageSize);

    PageInfo<InspectionData> getInspectionDataByRecordID(Integer inspectionDataID, int pageNum, int pageSize);

    void save(InspectionData inspectionData);

    InspectionData getInspectionDataByID(String id);

    void delete(String inspectionDataID);

    void update(InspectionData inspectionData);
}