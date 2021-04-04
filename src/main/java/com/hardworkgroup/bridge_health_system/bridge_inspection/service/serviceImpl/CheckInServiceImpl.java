package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.CheckInDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.CheckInService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.CheckIn;
import lombok.extern.slf4j.Slf4j;
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
public class CheckInServiceImpl implements CheckInService {

    @Resource
    private CheckInDao checkInDao;

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<CheckIn> findAll(int pageNum, int pageSize) {
        Page<CheckIn> page = PageHelper.startPage(pageNum,pageSize);
        List<CheckIn> checkIns =  checkInDao.selectAllCheckIn();
        return new PageInfo<>(checkIns,5);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<CheckIn> findAllByUserID(Integer userID, int pageNum, int pageSize) {
        Page<CheckIn> page = PageHelper.startPage(pageNum,pageSize);
        List<CheckIn> checkIns =  checkInDao.selectAllByUserID(userID);
        return new PageInfo<>(checkIns,5);
    }

    /**
     * 保存用户
     */
    @Override
    public void save(CheckIn checkIn) {
        //调用dao保存用户
        checkInDao.insertByKey(checkIn);
    }

    /**
     * 根据ID查找用户
     */
    @Override
    public CheckIn getCheckInByID(String checkInID) {
        return  checkInDao.getCheckInByID(checkInID);
    }

    /**
     * 删除用户
     */
    @Override
    public void delete(String checkInID) {
        checkInDao.deleteByKey(checkInID);
    }

    /**
     * 修改用户
     */
    @Override
    public void update(CheckIn checkIn) {
        checkInDao.updateByKey(checkIn);
    }
}