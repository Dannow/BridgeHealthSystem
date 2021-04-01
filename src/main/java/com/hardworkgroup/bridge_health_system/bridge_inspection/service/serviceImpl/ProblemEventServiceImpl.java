package com.hardworkgroup.bridge_health_system.bridge_inspection.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hardworkgroup.bridge_health_system.bridge_inspection.dao.ProblemEventDao;
import com.hardworkgroup.bridge_health_system.bridge_inspection.service.ProblemEventService;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity.ProblemEvent;
import com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.response.SimpleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ProblemEventDao problemEventDao;

    /**
     * 获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<SimpleEvent> findAll(int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllProblemEvent();
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : problemEvents) {
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        return new PageInfo<>(simpleEvents,5);
    }

    /**
     * 根据bridgeID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<SimpleEvent> findAllByBridgeID(Integer bridgeID, int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByBridgeID(bridgeID);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : problemEvents) {
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        return new PageInfo<>(simpleEvents,5);
    }

    /**
     * 根据planID获取所有巡检记录列表
     * @return 巡检记录结果
     */
    @Override
    public PageInfo<SimpleEvent> getProblemEventByRecordID(Integer inspectionRecordID, int pageNum, int pageSize) {
        Page<ProblemEvent> page = PageHelper.startPage(pageNum,pageSize);
        List<ProblemEvent> problemEvents =  problemEventDao.selectAllByRecordID(inspectionRecordID);
        List<SimpleEvent> simpleEvents = new ArrayList<>();
        for (ProblemEvent problemEvent : problemEvents) {
            simpleEvents.add(new SimpleEvent(problemEvent));
        }
        return new PageInfo<>(simpleEvents,5);
    }

    /**
     * 保存用户
     */
    @Override
    public void save(ProblemEvent problemEvent) {
        //调用dao保存用户
        problemEventDao.insertByKey(problemEvent);
    }

    /**
     * 根据ID查找用户
     */
    @Override
    public ProblemEvent getProblemEventByID(String id) {
        return  problemEventDao.getProblemEventByID(id);
    }

    /**
     * 删除用户
     */
    @Override
    public void delete(String problemEventID) {
        problemEventDao.deleteByKey(problemEventID);
    }

    /**
     * 修改用户
     */
    @Override
    public void update(String id, ProblemEvent problemEvent) {
        /*ProblemEvent tempProblemEvent = problemEventDao.getProblemEventByID(id);
        tempProblemEvent.setInspectionRecordID(problemEvent.getInspectionRecordID());
        tempProblemEvent.setSensorID(problemEvent.getSensorID());
        tempProblemEvent.setUserID(problemEvent.getUserID());
        tempProblemEvent.setProblemCreationTime(problemEvent.getProblemCreationTime());
        tempProblemEvent.setMaintenanceStatus(problemEvent.getMaintenanceStatus());
        tempProblemEvent.setConfirmStatus(problemEvent.getConfirmStatus());
        tempProblemEvent.setProblemTitle(problemEvent.getProblemTitle());
        tempProblemEvent.setProblemDescription(problemEvent.getProblemDescription());*/
        problemEventDao.updateByKey(problemEvent);
    }
}