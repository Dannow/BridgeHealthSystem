package com.hardworkgroup.bridge_health_system.realTime_dataCollection.service;

import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;

public interface PushService {
    /**
     * 推送给指定用户
     */
    void pushMsgToOne(User user, Object msg);

    /**
     * 推送给所有用户
     */
    void pushMsgToAll(String msg);
}
