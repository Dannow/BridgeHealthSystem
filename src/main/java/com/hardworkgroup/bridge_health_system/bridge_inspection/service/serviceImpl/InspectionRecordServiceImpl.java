package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionRecordService;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionRecordDao;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionRecord;

import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TPatrolRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Slf4j
@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Autowired
    private InspectionRecordDao inspectionRecordDao;

    @Override
    public InspectionRecord selectInspectionRecord(int inspectionID) {
        return inspectionRecordDao.selectByPrimaryKey(inspectionID);
    }

    /**
     * 保存用户
     */
    @Override
    public void save(InspectionRecord inspectionRecord) {
        //调用dao保存用户
        inspectionRecordDao.insertByKey(inspectionRecord);
    }

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionRecord> findAll(int pageNum, int pageSize) {
        Page<InspectionRecord> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllInspectionRecord();
        PageInfo<InspectionRecord> pageInfo = new PageInfo<>(inspectionRecords,5);
        return pageInfo;
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionRecord> findAllByPlanID(Integer inspectionPlanID, int pageNum, int pageSize) {
        Page<InspectionRecord> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionRecord> inspectionRecords =  inspectionRecordDao.selectAllByPlanID(inspectionPlanID);
        PageInfo<InspectionRecord> pageInfo = new PageInfo<>(inspectionRecords,5);
        return pageInfo;
    }

    @Override
    public InspectionRecord getRecordByID(String id) {
        return  inspectionRecordDao.getRecordByID(id);
    }

    @Override
    public void delete(String inspectionRecordID) {
        inspectionRecordDao.deleteByKey(inspectionRecordID);
    }

    @Override
    public void update(InspectionRecord inspectionRecord) {
        /*InspectionRecord tempRecord = inspectionRecordDao.getRecordByID(id);
        tempRecord.setInspectionRecordTitle(inspectionRecord.getInspectionRecordTitle());
        tempRecord.setInspectionContentDescription(inspectionRecord.getInspectionContentDescription());
        tempRecord.setInspectionTime(inspectionRecord.getInspectionTime());*/
        inspectionRecordDao.updateByKey(inspectionRecord);
    }
}