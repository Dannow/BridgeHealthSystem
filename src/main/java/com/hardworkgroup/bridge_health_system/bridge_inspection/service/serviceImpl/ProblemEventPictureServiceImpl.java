package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventPictureDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.ProblemEventPictureService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEventPicture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TPatrolRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Slf4j
@Service
public class ProblemEventPictureServiceImpl implements ProblemEventPictureService {

    @Resource
    private ProblemEventPictureDao problemEventPictureDao;

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<ProblemEventPicture> findAll(int pageNum, int pageSize) {
        Page<ProblemEventPicture> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEventPicture> ProblemEventPictures =  problemEventPictureDao.selectAllProblemEventPicture();
        return new PageInfo<>(ProblemEventPictures,5);
    }

    /**
     * 根据bridgeID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<ProblemEventPicture> findAllByEventID(Integer problemEventID, int pageNum, int pageSize) {
        Page<ProblemEventPicture> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEventPicture> problemEvents =  problemEventPictureDao.selectAllByEventID(problemEventID);
        return new PageInfo<>(problemEvents,5);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    /*@Override
    public PageInfo<ProblemEvent> getProblemEventByRecordID(Integer inspectionRecordID, int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByRecordID(inspectionRecordID);
        PageInfo<ProblemEvent> pageInfo = new PageInfo<>(problemEvents,5);
        return pageInfo;
    }*/

    /**
     * 保存用户
     */
    @Override
    public void save(ProblemEventPicture problemEventPicture) {
        //调用dao保存用户
        problemEventPictureDao.insertByKey(problemEventPicture);
    }

    /**
     * 根据ID查找用户
     */
    @Override
    public ProblemEventPicture getProblemEventPictureByID(String id) {
        return  problemEventPictureDao.getProblemEventPictureByID(id);
    }

    /**
     * 删除用户
     */
    @Override
    public void delete(String problemEventPictureID) {
        problemEventPictureDao.deleteByKey(problemEventPictureID);
    }

    /**
     * 修改用户
     */
    @Override
    public void update(String id, ProblemEventPicture problemEventPicture) {
        problemEventPictureDao.updateByKey(problemEventPicture);
    }
}