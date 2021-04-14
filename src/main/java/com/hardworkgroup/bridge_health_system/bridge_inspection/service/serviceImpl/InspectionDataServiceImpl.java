package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.InspectionDataDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.InspectionDataService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.InspectionData;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionData)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Slf4j
@Service
public class InspectionDataServiceImpl implements InspectionDataService {

    @Resource
    private InspectionDataDao inspectionDataDao;

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionData> findAll(int pageNum, int pageSize) {
        Page<InspectionData> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionData> inspectionData =  inspectionDataDao.selectAllInspectionData();
        return new PageInfo<>(inspectionData,5);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<InspectionData> findAllByRecordID(Integer recordID, int pageNum, int pageSize) {
        Page<InspectionData> page = PageHelper.startPage(pageNum,pageSize);
        List<InspectionData> inspectionData =  inspectionDataDao.selectAllByRecordID(recordID);
        return new PageInfo<>(inspectionData,5);
    }

    /**
     * 保存用户
     */
    @Override
    public void save(InspectionData inspectionData) {
        //调用dao保存用户
        inspectionDataDao.insertByKey(inspectionData);
    }

    /**
     * 根据ID查找用户
     */
    @Override
    public InspectionData getInspectionDataByID(String id) {
        return  inspectionDataDao.getInspectionDataByID(id);
    }

    /**
     * 删除用户
     */
    @Override
    public void delete(String inspectionDataID) {
        inspectionDataDao.deleteByKey(inspectionDataID);
    }

    /**
     * 修改用户
     */
    @Override
    public void update(InspectionData inspectionData) {
        inspectionDataDao.updateByKey(inspectionData);
    }
}