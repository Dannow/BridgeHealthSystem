package com.hardworkgroup.bridge_health_system.activiti.service.serviceImpl;

import com.hardworkgroup.bridge_health_system.activiti.dao.SiteMessageDao;
import com.hardworkgroup.bridge_health_system.activiti.service.SiteMessageService;
import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.SiteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteMessageServiceImpl implements SiteMessageService {

    @Autowired
    private SiteMessageDao siteMessageDao;
    @Override
    public List<SiteMessage> findAll(Integer userID) {
        return siteMessageDao.selectMsgList(userID);
    }
    @Override
    public SiteMessage findOne(Integer id, Integer userID) {
        return siteMessageDao.selectOneMsg(id,userID);
    }
    @Override
    public SiteMessage findOne(Integer userID) {
        return siteMessageDao.selectOne(userID);
    }
    @Override
    public int readMsg(Integer id, Integer userID) {
        return siteMessageDao.updateMsgRead(id,userID);
    }
    @Override
    public int sendMsg(Integer userID, String taskId, Integer type) {
        return siteMessageDao.insertMsg(userID,taskId,type);
    }
}
