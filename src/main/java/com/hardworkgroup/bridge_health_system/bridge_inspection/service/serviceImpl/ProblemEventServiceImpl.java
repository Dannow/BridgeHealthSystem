package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventPictureDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.ProblemEventService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleEventPicture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TPatrolRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 18:08:33
 */
@Slf4j
@Service
public class ProblemEventServiceImpl implements ProblemEventService {

    @Resource
    private ProblemEventDao problemEventDao;

    @Resource
    private ProblemEventPictureDao problemEventPictureDao;

    /**
     * 获取所有问题事件列表
     * @return 问题事件结果
     */
    @Override
    public PageInfo<ProblemEvent> findAll(int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllProblemEvent();
        return new PageInfo<>(problemEvents,5);
    }

    /**
     * 手机端获取所有问题事件列表
     * @return 问题事件结果
     */
    @Override
    public List<SimpleEvent> findAll() {
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllProblemEvent();
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : problemEvents) {
            problemEvent.setProblemEventPictures(problemEventPictureDao.selectAllByEventID(problemEvent.getProblemEventID()));
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        return simpleEvents;
    }

    /**
     * 根据bridgeID获取所有问题事件列表
     * @return 问题事件结果
     */
    @Override
    public PageInfo<ProblemEvent> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByBridgeID(bridgeID);
        return new PageInfo<>(problemEvents,5);
    }

    /**
     * 根据bridgeID获取所有问题事件列表
     * @return 问题事件结果
     */
    @Override
    public List<SimpleEvent> findAllByBridgeID(Integer bridgeID) {
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByBridgeID(bridgeID);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : problemEvents) {
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        return simpleEvents;
    }

    /**
     * 根据planID获取所有问题事件列表
     * @return 问题事件结果
     */
    @Override
    public PageInfo<ProblemEvent> getProblemEventByRecordID(Integer inspectionRecordID, int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByRecordID(inspectionRecordID);
        return new PageInfo<>(problemEvents,5);
    }

    /**
     * 保存问题事件
     */
    @Override
    public void save(ProblemEvent problemEvent) {
        //调用dao保存用户
        problemEventDao.insertByKey(problemEvent);
    }

    /**
     * 根据ID查找问题事件
     */
    @Override
    public ProblemEvent getProblemEventByID(String id) {
        return  problemEventDao.getProblemEventByID(id);
    }

    /**
     * 删除问题事件
     */
    @Override
    public void delete(String problemEventID) {
        problemEventDao.deleteByKey(problemEventID);
    }

    /**
     * 修改问题事件
     */
    @Override
    public void update(String id, ProblemEvent problemEvent) {
        problemEventDao.updateByKey(problemEvent);
    }
}