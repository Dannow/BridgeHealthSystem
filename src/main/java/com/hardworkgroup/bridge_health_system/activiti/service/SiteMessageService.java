package com.hardworkgroup.bridge_health_system.activiti.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.activiti.entity.SiteMessage;

import java.util.List;

public interface SiteMessageService {
    List<SiteMessage> findAll(Integer userID);

    SiteMessage findOne(Integer id, Integer userID);

    SiteMessage findOne(Integer userID);

    int readMsg(Integer id, Integer userID);

    int sendMsg(Integer userID, String taskId, Integer type, Integer isRead);
}
